## 概要
編集途中でまとまってないです。
説明会でやるには分量が多すぎたけどもったいないので残しておく用。

## composeファイルとタスク定義の変換がうまくいかない場合

### 例1: Fargate用のJSONを変換する場合(ハンズオン4)
nginxなど外部からの接続が必要なコンテナについては、クラスターとコンテナの間でポートマッピングを行う必要があります。
(ハンズオン1でもポートマッピングを設定していました)。

Fargateを用いる場合、基本的には明示的にポートマッピングを設定しないことが多いです。（自動で同じポート番号にマッピングされるため）
この状態でタスク定義をcomposeファイルに変換すると、マッピングに関する設定がされていないcomposeファイルが生成されます。
handson/4に入ってecs-cli local createを実行しましす。
```
ecs-cli local create
```
生成されたcomposeファイルを先程(ハンズオン3)のものと見比べてみてください。
port設定がないことがわかります。
したがってこのcomposeファイルを使ってローカルで開発を行いたければ、マッピングに関する設定を追加してやる必要があります。

設定の追加を行うには先程composeファイルと一緒に生成されたoverride.ymlを編集します。

```
version: "3.4"
services:
  nginx:
    environment:
      AWS_CONTAINER_CREDENTIALS_RELATIVE_URI: /creds
    logging:
      driver: json-file
    # 追記
    ports:
      - target: 80
        published: 9090

```
overrideオプションを付けてecs-cli local upします。
(log driverの警告が出ますがYesで進めてください)
```
ecs-cli local up --override docker-compose.ecs-local.override.yml
```
localhost:9090にアクセスすると無事nginxの初期画面が表示されました。
このようにoverrideにオプションを追加することで、元のymlを上書きすることができます。

しかし注意したいのが、overrideを使うだけでは対応できない場合があります。
実際に問題が起こるケースを次の項目で見てみます。

### 例2: volumesを使う場合「タスク定義->compose編」（ハンズオン5）
タスク定義でボリュームを設定している場合、このタスク定義からcomposeファイルを生成するとエラーが起きます。

```
ecs-cli compose --project-name 適当な名前 create
```
"field Source must not be empty"というエラーが発生すると思います。
マウント元のソースがないと言われるのでソースを指定してやる必要があります。

しかし、このbindの項目については編集も上書きもできません（多分）。
実際にやってみましょう。

// 編集途中: bindでoverrideする

引き続きエラーが出ると思います。
これはbindが上書きされるのではなく追加で設定されてしまうことが原因です。
volumesに書き換えてもこれは同様です。
したがって、override.ymlではなく元のcomposeファイルを直接書き換える必要が出てきます。

ecs-cliとvolumesの相性は悪く、逆（compose->タスク定義）の変換でもエラーが起きてしまいます。
次の項目ではそれについて確認します。

### 例3: volumes driverを使う場合「compose->タスク定義編」（ハンズオン6）
volumes driverを設定している場合エラーが出ます。
/handson/6で以下のコマンドを実行します。
```
ecs-cli compose --project-name かぶらない名前 create 
```
"volume driver is not supported"というエラーが出ると思います。
エラー内容の通りvolumesに対応していないので、composeファイルからdriverを削除してタスク定義を生成した後、手動でタスク定義を編集してやる必要があります。

### 例4: composeファイルのバージョン問題
現在composeファイル->タスク定義の変換がサポートされているのはメジャーバージョン(1, 1.0, 2, 2.0, 3, 3.0)のみになっています。
しかしこれまでに生成したcomposeファイルを見てみるとバージョンは3.4。
つまり、生成されたcomposeファイルはそのままではタスク定義に再度変換することができません。

## まとめ
- ecs-cliは現状使いにくい
- 大体volumesが悪い
- 将来に期待