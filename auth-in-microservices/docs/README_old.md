# Authentication and Authorization in Microservices

マイクロサービスにおける認証と認可について。

## 要求定義

- ソーシャルログイン
- EC サイト・業務基幹システム・Redmine 等の認証サービスを統合
  - セキュリティは ip 制限と認可スコープによって分離する（今までと似ている）
  - ただし統合の有用性と意味（特に EC と基幹システム）を十分検討する

## 要件定義

### 機能一覧

- 新規登録
- ログイン
- パスワードリセット
- ログアウト
- ユーザー情報取得

## 認証/認可の技術キーワード

- Amazon API Gateway
  - Lambda オーソライザー
  - Cognito オーソライザー
- OAuth
- OpenID connect
- ベアラートークン
  - JWT(JSON Web Token)
- Spring Security

## 認証/認可のスコープ

認証/認可基盤が取り扱うものは大きく分けて以下の 2 つ。

- API Gateway への認証
- API Gateway 以降のマイクロサービス間 API 通信の認可

ただし後者も含めて考えると複雑性が上がるため、ひとまず前者だけをスコープとする。

## Amazon API Gateway で使用できる認証について

API Gateway にはいくつか API タイプがあるが、マイクロサービス化にあたって候補となるのは REST API と HTTP API の 2 つ。

それぞれで使用できる認証(認可)方法は以下の通り。

- REST API
  - Cognito ユーザープール
    - AWS のマネージドな認証サービス
  - Lambda オーソライザー
    - 認証/認可のロジックがカスタム可能（自前で用意する）
- HTTP API
  - JWT オーソライザー
    - OpenID connect や OAuth などの JWT をマネージドで検証可能

### REST API と HTTP API の比較

- [HTTP API または REST API の選択](https://docs.aws.amazon.com/ja_jp/apigateway/latest/developerguide/http-api-vs-rest.html)
- [Amazon API Gatewayは「HTTP API」と「REST API」のどちらを選択すれば良いのか？](https://dev.classmethod.jp/articles/amazon-api-gateway-http-or-rest/)

### REST API の認証/認可について追加情報

以下のリンクでは他にも API キーを用いた方法も紹介されている（ただし認証として推奨されている方法は上記の 2 つ）。

- [API Gateway での REST API へのアクセスの制御と管理（AWS 公式ドキュメント）](https://docs.aws.amazon.com/ja_jp/apigateway/latest/developerguide/apigateway-control-access-to-api.html)
- [API Gatewayと4つの認証方法](https://www.greptips.com/posts/1294/)
- [Amazon API GatewayでAPIキー認証を設定する](https://dev.classmethod.jp/articles/apigateway-apikey-auth/)

## 参考

### [マイクロサービス時代のセッション管理](https://engineer.retty.me/entry/2019/12/21/171549)

> "4. ゲートウェイ分散型 - Opaque Token"

このパターンを Amazon API Gateway を用いて実現する方針。

### [Backend Engineer’s meetup マイクロサービスにおける認証認可基盤](https://dev.classmethod.jp/articles/merpay-microservice-auth/)

メルカリのマイクロサービスにおける認証認可基盤に関する LT のレポート。

（メルカリの認証基盤チームについて→[メルペイの認証基盤チームのバックエンドエンジニアを募集します](https://www.pospome.work/entry/2019/06/12/125841)）

#### ポイント

- 内部通信の認証認可は内部トークンで行っている
- 内部トークンは JWT を採用
- 各マイクロサービスで内部トークン用のコードの重複を避けるため、認証基盤チームが内部トークン用の SDK を提供している（Golang のみ）
- （オプション）3rd パーティクライアントに提供するトークンのスコープ（外部スコープ）と内部トークンのスコープ（内部トークン）は一対一にしない
  - 外部スコープはユースケースベース
  - 内部スコープはリソースベース
  - 認証基盤で外部スコープを内部スコープとマッピングしている

キングプリンターズでは 3rd パーティクライアントはないので、前半を特に参考にする。

### [AWS Lambdaを使用したマイクロサービスの構築](https://dev.classmethod.jp/articles/aws-reinvent2019-report-svs343/)

認証/認可との関係は薄いが、マイクロサービスの内部通信ついては参考になる。

### [DHARMA: マイクロサービスアーキテクチャのSecurity by Design](https://yunkt.hatenablog.com/entry/2019/11/02/205521)

マイクロサービス間の通信のセキュリティについて。

### [OpenID の概要](https://www.mhlw.go.jp/content/10800000/000537444.pdf)

野村総合研究所による OAuth/OpenID/OpenId connect などの解説資料。
