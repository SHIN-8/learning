# 料金見積

Amazon Cognito (以下 Cognito) を導入した場合と，コンテナ基盤(ECS) のサーバーを構築する場合の料金見積を行う。

## 前提

導入を検討している Cognito は月間アクティブユーザー数 (認証操作を行ったユーザー数。以下 MAU) に応じた課金が発生するが，現在の当社のシステムでは MAU が集計できないため，注文のある customer_id の数を相当数とする。

月毎に注文のある cutomer_id の数は以下の SQL で集計する。

```sql
select
    count(distinct customer_id) as customer_count,
    date_format(created_at, '%Y-%m') as monthly
from
    orders
where
    created_at > '2019-01'
group by
    date_format(created_at, '%Y-%m');
```

集計の結果は以下の通り。2019年の月間平均注文顧客数は**約4608名**である。

| customer_count | monthly |
|--:|:--:|
| 4230 | 2019-01 |
| 4436 | 2019-02 |
| 5287 | 2019-03 |
| 4810 | 2019-04 |
| 4476 | 2019-05 |
| 4427 | 2019-06 |
| 4587 | 2019-07 |
| 4169 | 2019-08 |
| 4708 | 2019-09 |
| 4821 | 2019-10 |
| 4659 | 2019-11 |
| 4688 | 2019-12 |
| **4608/month** | |

本見積に使用する MAU としては，今後の注文数増加を見越して 10,000 MAU として計算する。

またその内 OpenID Connect 等の外部認証プロバイダーを利用する割合を 20% (2,000 MAU)と仮定する。

## Cognito

Cognito を使用する場合，Cognito 本体と，Cognito にリクエストする API として AWS Lambda を使用するアーキテクチャとする。

[Cognito の料金について](https://aws.amazon.com/jp/cognito/pricing/)

> Cognito ユーザープール機能には、Cognito ユーザープールに直接サインインするユーザーの場合は 50,000 MAU、SAML 2.0 ベースの ID プロバイダーを介してフェデレーションされるユーザーの場合は 50 MAU の無料利用枠があります。

ただし「Cognito ユーザープールに直接サインインするユーザー」には以下のソーシャルログインも含まれる。

> ユーザープールからの認証情報または Apple、Google、Facebook、Amazon などのソーシャル ID プロバイダーで直接サインインするユーザー

また SAML 2.0 だけでなく，OpenID Connect も利用可能。

> SAML または OIDC フェデレーションを使用してサインインするユーザーの場合、50 MAU の無料利用枠を超える MAU の料金は 0.015USD です。

まとめると以下の通り。

- Cognito
  - Cognito ユーザー:
    - 8,000 MAU = 0 USD (無料利用枠内)
  - SAML 2.0 or OIDC 利用:
    - 2,000 MAU = 2000 * 0.015 USD = 30 USD

- Lambda
  - リクエスト数は MAU の 10 倍と仮定
  - 同時実行数は 5 と仮定（同時リクエスト数が予測できないため適当）

| Region | Service | Upfront | Monthly | First 12 months total| Currency | Configuration summary |
|:--:|:--:|:--:|:--:|:--:|:--:|:--:|
| Asia Pacific (Tokyo) | AWS Lambda | 0 | 17.67 | 212.04 | USD | リクエストの数 (100000), 同時実行性 (5), プロビジョニングされた同時実行が有効になっている時間 (720 時間) |

計 30 + 17.67 = **<u>47.67 USD/Month</u>**

## コンテナ基盤(ECS)

ECS は EC2 起動タイプとし，アーキテクチャは ECS + ELB(Application Load Balancer) とする。

- ECS (EC2 タイプ)
  - それほどの性能は不要と考えられるため，インスタンスタイプは m6g.medium
- ELB

| Region | Service | Upfront | Monthly | First 12 months total| Currency | Configuration summary |
|:--:|:--:|:--:|:--:|:--:|:--:|:--:|
| Asia Pacific (Tokyo) | Amazon EC2 | 0 | 39.73 | 476.76 | USD | オペレーティングシステム (Linux), 数量 (1), 各 EC2 インスタンスのストレージ (汎用 SSD (gp2)), ストレージ量 (30 GB), インスタンスタイプ (m6g.medium) |
| Asia Pacific (Tokyo) | Elastic Load Balancing | 0 | 23.58 | 282.96 | USD | Application Load Balancer の数 (1) |

計 39.73 + 23.58 = **<u>63.31 USD/Month</u>**
