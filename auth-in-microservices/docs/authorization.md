# 認可について

認可については API Gateway では必要最低限（認証されているかどうか程度）とし，各エンドポイントに対する認可はマイクロサービス内，もしくはそのサイドカープロキシで行うことととする。

その理由は，API Gateway ができる認可はエンドポイント単位のため，もし API Gateway で認可を行う場合は各マイクロサービスのエンドポイントをすべて API Gateway に登録しなければならず，その場合 API Gateway と各マイクロサービスが密結合になるためである。

## 具体例

Amaryllis API (例: https://amaryllis-api.com/) を例に取って説明する。

### 悪い例

以下のエンドポイントを API Gateway に登録するとする。

- GET: https://amaryllis-api.com/compilation/get-compilation/{id}
- POST: https://amaryllis-api.com/data-check/{id}/good-data
- PATCH: https://amaryllis-api.com/reception/order-item
- ...

そしてすべてのエンドポイントに対して API Gateway で認可を行うとすると，API を新規作成するたびに以下の作業が必要になる。

- Amaryllis API で API 実装
- 実装したエンドポイントを API Gateway に登録
- 登録したエンドポイントへの認可範囲を設定

つまり新規 API を作成するたびに，マイクロサービスと API Gateway の両方で変更を行う必要がある。

### 良い例

ワイルドカードを使用して以下のエンドポイントだけを API Gateway に登録する。

- ANY(GET, POST, PUT, DELETE, HEAD, PATCH): https://amaryllis-api.com/*

各エンドポイントの認可は Amaryllis API で実装する。

こうすることで新しく API を開発した際も，マイクロサービスの開発だけで済むことになる。
