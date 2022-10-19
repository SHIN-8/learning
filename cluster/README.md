# HAクラスタに依る障害対応とその仕組み

## 動作概要
Corosyncがノード死活監視を行う。  
ノードがダウンした場合にはPacemakerへ通知し、リソース制御(ノード切り離し＋待機系ノードへのリソース引継ぎ)が実行される。

---

## 用語説明

### HAクラスタ
コンピュータシステムの可用性（アベイラビリティ）を高めることを目的とした、  
複数台のコンピュータによる連携構成（クラスタ）のことである。  
主にサーバーを対象として構築される。  
HAクラスタでは、複数台のサーバーが相互接続され、システムの冗長化が図られる。

### ノード
クラスタを構成するサーバー

### フェイルオーバー
稼働中のシステムで問題が生じてシステムやサーバーが停止してしまった際に、**自動的**に待機システムに切り替える仕組み  
*人間に依る手動切替はスイッチオーバー*

### リソース
Pacemaker（後述）にて扱われる監視・制御対象

---

## 使用するソフトウェア
### Pacemaker
HAクラスタソフト(リソースマネージャクラスタ)。  
Corosyncから障害通知を受け取って、praimary機 / secondary機の切り替えを制御する。

* マシン/アプリケーションレベルの障害検出と回復
* 任意の冗長構成サポート
* 複数マシン障害に対応
* 複数マシンでアクティブにする必要があるアプリケーションをサポート
* 複数モードアプリケーション(マスター/スレーブ)をサポート等

*Pacemakerの役割は、あくまでもリソース（アプリケーション）の管理であり、  
実際の各アプリケーションの起動、停止などの制御は「リソースエージェント」が実施する。*

* リソースエージェント  
アプリケーションの起動、停止などを行う。(シェルスクリプトやバイナリ）

### Corosync
HAクラスタ構成の中でクラスタ通信層制御を担当するクラスタ基盤ソフトウェア。  
クラスタ通信フレームワークを提供し、クラスタ構成サーバ間でノードの死活監視を行う。

* 仮想同期保証を備えた閉鎖プロセスグループ通信モデル(複製された状態マシンを作成)
* シンプルアベイラビリティマネージャ(アプリケーションプロセスが失敗した場合にアプリケーションプロセスを再起動)
* 構成および統計インメモリデータベース(情報の変更通知を設定/取得/受信)等

##### Pacemakerとの連携
Corosyncはクラスタ通信層制御機能は持つが、リソース監視/制御機能を持っていない。  
その為、リソース監視/制御機能を持つPacemakerと連携させて、クラスタシステムを構成する。

---

### Nyxの構成
#### ネットワーク対応関係

| NIC | primary | secondary | VIP | use |
|:-----:|:-----:|:-----:|:-----:|:-----:|
| enp2s0 | 192.168.2.121 | 192.168.2.122 | 192.168.2.235 | Nyxサービス |
| enp0s25 | 12.0.1.1 | 12.0.1.2 | 無し | 死活監視・DRBDデータ同期 |
| enx1cc0350364** | 127.0.0.1 | 127.0.0.2 | 無し | 死活監視サブ |

#### Pacemakerリソース

| name | kind | use |
|:-----:|:-----:|:-----:|
| res_drbd_nyx | ocf::linbit:drbd | データ同期 |
| res_ip | ocf::heartbeat:IPaddr2 | VIP |
| res_filesystem | ocf::heartbeat:Filesystem | DRBD上のファイルシステム |
| res_nginx | ocf::heartbeat:nginx | Nyx用webサーバ |
| res_mattermost | ocf::heartbeat:MattermostTo | メッセージ送信 |

---

### フェイルオーバー

#### 発生条件
* メイン機が停止
* 監視用ネットワーク障害
* リソースの破損
* HDDのI/O故障（正確にはDRBDが感知し、自身をdetachする事で異常としてPacemakerが感知）

#### イベントフック
フェイルオーバーが発生した時にはイベントが発生する。  
それをリソースに関連付けして通知等を飛ばす事が出来る。  
（mattermostへ飛んでくる通知はデフォルトで用意されていたメール通知を改造したもの）

#### 手動切り替え
手動での切り替えも可能。  
slaveにしたいノードを待機状態にし、それから全起動させる

```bash
pcs cluster standby nyx-primary
pcs cluster unstandby --all
```

---

#### クラスタのGUIに依る確認
管理用コマンドpcsをインストールすると自動で付いてくる

[https://192.168.2.235:2224/](https://192.168.2.235:2224/)

cluster管理ユーザ（OSユーザではない）  

|項目|値|
|:-----:|:-----:|
|ユーザ|hacluster|
|パスワード|$h9y3k!5bx|

---

## shell scriptとdockerで簡易failover体験

| コンテナ名 | アドレス |用途|
|:-----:|:-----:|:-----:|
| primary | 172.0.0.3 |クラスタメイン機|
| secondary | 172.0.0.4 |クラスタサブ機|
| client | 172.0.0.5 |クライアント（ping係）|

###### VIP : 172.0.0.10

### 確認・動作概要
1. clientよりVIPにpingを送る→返ってこない
1. primaryにてVIPを自身に割り当てる
1. clientよりping確認→返ってくる
1. secondaryにてVIPの監視を行わせる
1. primaryを落とす
1. secondaryがVIPの異常を検知し、自身にVIPを再割当てする
1. clientよりping確認→未だに返ってくる

### 手順
1. `docker-compose up -d`でコンテナ立ち上げ
1. 窓を３つ立ち上げそれぞれのコンテナ内に`docker exec -ti コンテナ名 /bin/sh`で入る
1. clientより`172.0.0.10`にping(そのままにしておく
1. primaryにて`sh failover.sh`を実行→failover!という文字列が表示され、VIP割り当て
1. clientを確認→反応が返ってきている
1. secondaryにて`sh failover.sh`を実行→health check ok!という文字列が延々と表示される
1. primaryにて`halt -f`を実行してOSを落とす
1. secondaryを確認→failover!という文字列が出力される
1. clientを確認

### ネットワーク構成
docker-compose.yml
```yaml
version: "3"

services:
  primary:
    image: 192.168.2.47:5000/cart/nginx:0.1
    container_name: primary
    volumes:
      - ./failover.sh:/failover.sh
      - ./nginx.conf:/etc/nginx/nginx.conf
    command: ["nginx", "-g", "daemon off;"]
    privileged: true # dockerネットワークでIPを割り当てる為に必要
    networks: # 属するネットワーク
      app_net:
        ipv4_address: 172.0.0.3
  secondary:
    image: 192.168.2.47:5000/cart/nginx:0.1
    container_name: secondary
    volumes:
      - ./failover.sh:/failover.sh
      - ./nginx.conf:/etc/nginx/nginx.conf
    command: ["nginx", "-g", "daemon off;"]
    privileged: true # dockerネットワークでIPを割り当てる為に必要
    networks: # 属するネットワーク
      app_net:
        ipv4_address: 172.0.0.4
  client:
    container_name: client
    image: 192.168.2.47:5000/cart/nginx:0.1
    command: ["nginx", "-g", "daemon off;"]
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
    networks:
      app_net:
        ipv4_address: 172.0.0.5

# 仮想イーサネット
networks:
  app_net:
    driver: bridge
    ipam:
      driver: default
      config:
        - subnet: 172.0.0.0/24
```

#### フェイルオーバー用スクリプト

failover.sh
```sh
#!/bin/bash

VIP="172.0.0.10"
DEV="eth0"

health_check() {
  arping -q -c 1 -I $DEV $VIP # VIPの割り当て確認
  return $?
}

ip_failover() {
  ip addr add $VIP/24 dev $DEV
  arping -q -A -I $DEV -c 1 $VIP # VIPを自身に割り当てる
}

while health_check; do
  echo "health check ok!"
  sleep 1
done

echo "failover!"

ip_failover
```

### IP引き継ぎの仕組み
#### 概要
Ethernetでは、IPアドレスではなくNICのMACアドレスを使って通信を行う。  
他のサーバにパケットを送る際、MACアドレスを取得するために、  
ARP（Address Resolution Protocol）というプロトコルを用いる。  
ARPが保持しているIPとMACアドレスの対応関係を更新する事で、  
IPアドレスの引き継ぎを実現している。

#### ARPとは
IPアドレスを指定してMACアドレスを問い合わせるための仕組みである。  
しかし、通信するたびに問い合わせをしていては効率が悪い。  
その為、一度取得したMACアドレスはARPテーブルに格納して一定時間キャッシュする。

#### ARPテーブルの更新
別のサーバに同じIPアドレスが割り当てられたとしても、ARPテーブルが更新されるまではそのサーバと通信する事は出来ない。  
その為、IPアドレスを引き継ぐためには、他のサーバのARPテーブルが更新されなければならない。  
その手段としてgratuitous ARP（GARP）が有る。  
通常のARPリクエストは「このIPアドレスに対応するMACアドレスを教えろ」という問い合わせをするものである。  
対して、gratuitous ARPは「私のIPアドレスとMACアドレスはこれです」と他のサーバへ通知する。  
上記スクリプトでは`arping -q -A -I $DEV -c 1 $VIP`で、gratuitous ARPを送出・各ARPテーブルを更新している。

test
