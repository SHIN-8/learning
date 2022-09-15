# Docker で Vue.js + Spring REST をやってみる

## 想定する対象
+ Docker の初学者
+ Vue.js の初学者
+ Spring MVC の初学者

## 目的
+ Docker について初歩的な動作を実践してみます
+ Vue.js について初歩的な動作を実践してみます
+ Spring MVC について初歩的な動作を実践してみます

## 前提
+ Docker, Docker Compose が使用可能

```sh
$ docker --version
Docker version 20.10.5, build 55c4c88
```
```sh
$ docker-compose --version
docker-compose version 1.28.5, build c4eb3a1f
```

+ JDK, Maven が使用可能

```sh
$ java -version
java version "1.8.0_202"
Java(TM) SE Runtime Environment (build 1.8.0_202-b08)
Java HotSpot(TM) 64-Bit Server VM (build 25.202-b08, mixed mode)
```
```sh
$ mvn -version
Apache Maven 3.6.1 (d66c9c0b3152b2e69ee9bac180bb8fcc8e6af555; 2019-04-05T04:00:29+09:00)
```

## ゴール
+ アプリケーション の仕様

|No|URL|Docker イメージ|概要・用途|実装技術|備考|
|:--|:--|:--|:--|:--|:--|
|1|http://localhost:8880/index.html|nginx|フロントエンド|HTML, Javascript(Vue.js), css|WEBコンテンツを提供します|
|2|http://localhost:8881/api/message|jetty|バックエンド|Java(Spring MVC)|APIを提供します ※今回は RDBMS 部分は省略|

## 目次
1. Docker を使ってみる
1. HTML ファイルを表示させる
1. 配布可能な Docker アプリを作成する(※簡易版)
1. Vue.js を導入する
1. Ajax リクエスト処理の追加
1. バックエンドサーバーの構築
1. Java WEBアプリの作成
1. CSS フレームワークを適用

---
## Docker を使ってみる
+ 既存の Docker イメージを使ってみます

#### Docker イメージの取得
+ nginx Docker イメージを取得します

```sh
$ docker pull nginx:latest
```

+ 取得済みのイメージを確認します

```sh
$ docker images
REPOSITORY   TAG       IMAGE ID       CREATED      SIZE
nginx        latest    6084105296a9   7 days ago   133MB
```

#### コンテナの作成と実行
+ コンテナを作成して実行します

```sh
$ docker run --name nginx01 -d -p 8880:80 nginx:latest
```

+ WEBブラウザで確認します

```sh
http://localhost:8880/
※ Nginx コンテナが立ち上がっています
```

+ 同じイメージから別の nginx コンテナを立てることも出来ます
※ポートを別にします

```sh
$ docker run --name nginx02 -d -p 7770:80 nginx:latest
```

+ WEBブラウザで確認します

```sh
http://localhost:7770/
※ Nginx コンテナが立ち上がっています
```

#### コンテナの停止と削除
+ コンテナの停止

```sh
$ docker stop nginx01
$ docker stop nginx02
```

+ コンテナの再実行

```sh
$ docker start nginx01
```

+ コンテナの状態確認

```sh
$ docker ps
CONTAINER ID   IMAGE          COMMAND                  CREATED       STATUS         PORTS                  NAMES
29f2e4e799c7   nginx:latest   "/docker-entrypoint.…"   3 hours ago   Up 6 seconds   0.0.0.0:8880->80/tcp   nginx01
```

+ コンテナの削除

```sh
$ docker rm nginx01
$ docker rm nginx02
```

+ コンテナの状態確認(※停止したコンテナ含む)

```sh
$ docker ps -a
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES
```

#### Docker イメージの削除

+ Docker イメージの確認

```sh
$ docker images
REPOSITORY   TAG       IMAGE ID       CREATED      SIZE
nginx        latest    6084105296a9   7 days ago   133MB
```

+ Docker イメージの削除

```sh
$ docker rmi nginx:latest
```

+ Docker イメージの再確認

```sh
$ docker images
REPOSITORY   TAG       IMAGE ID       CREATED      SIZE
```

### <ここまでのまとめ>
+ 既に公開されている nginx の Docker イメージ を使用することが出来ます
+ nginx イメージ から複数の異なる設定の nginx コンテナを立ち上げることが出来ます
+ コンテナを起動、停止、削除することが出来ます
+ nginx イメージを削除することが出来ます
+ ここまでは Docker 環境の中で実行可能です

---
## HTML ファイルを表示させる
+ アプリ開発の為の特定のディレクトリが必要になります、以下を開発ディレクトリとしました

```sh
C:\Users\kpu0560\Desktop\app01
```

※以降、各自の環境で読み替えて下さい

+ 作成するファイルの概要

|No|ファイルPATH|拡張子|記述言語|概要・内容|
|:--|:--|:--|:--|:--|
|1|var\www\html\index.html|html|HTML|WEBコンテンツ|
|2|docker-compose.yml|yml|YML|Docker 起動用設定|

#### HTML コンテンツの作成
+ HTMLファイルの作成  
※以下のようにファイルを作成します

```sh
※アプリディレクトリ/var/www/html/index.html
```

```sh
※アプリディレクトリで
$ mkdir -p var/www/html
$ touch var/www/html/index.html
```

+ index.html の内容

```html
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>index</title>
  </head>
  <body>
    <div>It works!</div>
  </body>
</html>
```

#### Docker Compose の設定ファイルの作成
+ docker-compose.yml の作成  
※以下のようにファイルを作成します

```sh
※アプリディレクトリで
$ touch docker-compose.yml
```

+ docker-compose.yml の内容

```yml
version: '3'
services:
  nginx:
    image: nginx:latest
    ports:
      - "8880:80"
    volumes:
      - ./var/www/html:/usr/share/nginx/html
    container_name: nginx01
```

+ Docker Compose を実行します

```sh
※アプリディレクトリで
$ docker-compose up -d
Creating nginx01 ... done
```

+ WEBブラウザで確認します

```sh
http://localhost:8880/
※ It works! とWEBブラウザに表示されます
```

+ **＜ポイント＞**
    + `docker-compose.yml` の `volumes:` の項目でローカルPCの HTML コンテンツを nginx コンテナに渡して(マウントして)います
    + 上記の `docker-compose.yml` は以下の `docker run` コマンドとほぼ同義です

```sh
$ docker run -v "C:\Users\kpu0560\Desktop\app01\var\www\html:/usr/share/nginx/html" --name nginx01 -d -p 8880:80 nginx:latest 
```

### <ここまでのまとめ>
+ Docker 環境があればローカルのHTMLファイルを手軽に表示することが出来ます
+ 既存の nginx イメージを利用する場合 Dockerfile は必要ありません
+ `docker-compose.yml` は複雑なパラメータを持つ `docker run` コマンドの代用となります

---
## 配布可能な Docker アプリを作成する(※簡易版)
+ `app01` フォルダを丸ごと zip 圧縮なりして配布します
+ zip ファイルを解凍し `docker-compose.yml` が存在するディレクトリで `docker-compose up` します
+ もちろん相手側の環境に Docker 環境がインストールされている必要があります

### <ここまでのまとめ>
+ Docker コンテナとは Docker 環境で実行出来るアプリケーションといえます
+ Docker イメージとは Docker コンテナを作成するテンプレートとして使用されます
+ 静的WEBコンテンツを表示する場合、今回の設定例ですとアプリディレクトリの `/var/www/html` 配下にWEBコンテンツ(html, js, css, jpg, png, etc)を配置すれば手軽に nginx コンテナを立ち上げて確認することが出来ます

---
## Vue.js を導入する

+ 作成・修正するファイルの概要

|No|ファイルPATH|拡張子|記述言語|概要・内容|
|:--|:--|:--|:--|:--|
|1|var\www\html\index.html|html|HTML|WEBコンテンツ|
|2|var\www\html\scripts\app.js|js|javascript|WEBコンテンツ・コードビハインド|

#### テキストの表示
+ index.html の修正

```html
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <script src="https://cdn.jsdelivr.net/npm/vue@2.6.12"></script>
    <script src="/scripts/app.js" language="javascript" type="text/javascript"></script>
    <title>index</title>
  </head>
  <body>
    <div id="vue-app">
      <div>{{ message }}</div>
    </div>
  </body>
</html>
```

+ この段階でWEBブラウザで表示してみます

```sh
http://localhost:8880/
※ {{ message }} とWEBブラウザに表示されます
※ この状態では vue の処理が走っていません
```

+ app.js の作成  
※以下のようにファイルを作成します

```sh
※アプリディレクトリ/var/www/html/scripts/app.js
```

```sh
※アプリディレクトリで
$ mkdir -p var/www/html/scripts
$ touch var/www/html/scripts/app.js
```

+ app.js の内容

```js
// データオブジェクト
var data = { message: "It works!" };

window.addEventListener("load", () => {
    // Vue インスタンスにオブジェクトを追加する
    var vm = new Vue({
        el: '#vue-app',
        data: data
    })
});
```

+ WEBブラウザで確認します

```sh
http://localhost:8880/
※ It works! とWEBブラウザに表示されます
```

+ **＜ポイント＞**
    + `app.js` で javascript の `data` オブジェクトを Vue.js のリアクティブシステム `data` に設定しています
    + その結果 `data` オブジェクトの `message` プロパティにを `index.html` に変数名 `message` としてバインドすることが出来ます

#### ※参考: Vue のリアクティブシステムとは？

+ `index.html` をWEBブラウザ(Chrome)などで表示した状態で
    + Developer Tools(F12) を開きます
    + Console のタブに以下のように入力します

```sh
> data.messege="テスト"
※ エンターを押します
※ ページの表示にも "テスト" が反映されています
```

+ **＜ポイント＞**
    + javascript の `data` オブジェクトが Vue のリアクティブシステムに設定されているため自動的にHTML側の表示が更新されます
        + このような動作を 'リアクティブ' と呼びます

#### ボタンの追加

+ index.html の修正  
※以下のようにファイルを修正します

```html
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <script src="https://cdn.jsdelivr.net/npm/vue@2.6.12"></script>
    <script src="/scripts/app.js" language="javascript" type="text/javascript"></script>
    <title>index</title>
  </head>
  <body>
    <div id="vue-app">
      <div>{{ message }}</div>
      <button v-on:click="() => { message = 'Button Clicked!' }">Click</button>
    </div>
  </body>
</html>
```

+ WEBブラウザで確認します

```sh
http://localhost:8880/
※ [Call]ボタンを押すと Button Clicked! とWEBブラウザに表示されます
```

+ **＜ポイント＞**
    + `index.html` に追加したボタンに Vue の `v-on:click` ディレクティブを設定しています
    + この例ではボタンをクリックした時に実行される javascript の `関数` を直接記述しています
    + `message` は Vue に設定した `data.message` をが参照されています
    + Vue のリアクティブシステムにより HTML側の `message` も自動的に更新されます

#### イベントメソッドの実装

+ index.html の修正  
※以下のようにファイルを修正します

```html
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <script src="https://cdn.jsdelivr.net/npm/vue@2.6.12"></script>
    <script src="/scripts/app.js" language="javascript" type="text/javascript"></script>
    <title>index</title>
  </head>
  <body>
    <div id="vue-app">
      <div>{{ message }}</div>
      <button v-on:click="getMessage">Click</button>
    </div>
  </body>
</html>
```

+ WEBブラウザで確認します

```sh
http://localhost:8880/
※ 真っ白(エラー)になります 'getMessage' メソッドを記述していないからです
```

##### 関数を記述してみる(※参考)

+ app.js の修正  
※以下のように修正してみます

```js
// データオブジェクト
var data = { message: "It works!" };

// 関数(オブジェクト)
var getMessage = () => { message = 'Button Clicked!' };

window.addEventListener("load", () => {
    // Vue インスタンスにオブジェクトを追加する
    var vm = new Vue({
        el: '#vue-app',
        data: data
    })
});
```

+ WEBブラウザで確認します

```sh
http://localhost:8880/
※ [Call]ボタンを押しても何も変化がありません
```

+ さらに app.js の修正  
※以下のように修正してみます

```js
// データオブジェクト
var data = { message: "It works!" };

// 関数(オブジェクト)
var getMessage = (event) => { event.view.data.message = 'Button Clicked!' };

window.addEventListener("load", () => {
    // Vue インスタンスにオブジェクトを追加する
    var vm = new Vue({
        el: '#vue-app',
        data: data
    })
});
```

+ WEBブラウザで確認します

```sh
http://localhost:8880/
※ [Call]ボタンを押すと Button Clicked! とWEBブラウザに表示されます
```

+ **＜ポイント＞**
    + Vue の `v-on:click` ディレクティブから javascript の関数(オブジェクト)を呼び出せます
    + その際、イベント引数が暗黙的に渡って来ます
    + 【注意！】この方法は Vue の一般的な流儀ではありません、通常は次に説明する方法でイベントハンドラを記述します
        + 動作の理解のために説明しました

#### イベントハンドラを記述する(※実験版)

+ app.js の修正  
※以下のように修正します

```js
// データオブジェクト
var data = { message: "It works!" }

window.addEventListener("load", () => {
    // Vue インスタンスにオブジェクトを追加する
    var vm = new Vue({
        el: '#vue-app',
        data: data,
        // `methods` オブジェクトの下にメソッドを定義する
        methods: {
            getMessage: () => {
                data.message = 'Button Clicked!';
            }
        }
    })
});
```

+ WEBブラウザで確認します

```sh
http://localhost:8880/
※ [Call]ボタンを押すと Button Clicked! とWEBブラウザに表示されます
```

+ **＜ポイント＞**
    + Vue インスタンスに `methods` オブジェクトを追加し、`getMessage` 関数(オブジェクト)を記述します
    + HTMLページ `message` へのアクセスが `data.message` になる点に注意してください
    + これは グローバルなオブジェクト `var data` を参照しています
    + 【注意！】この方法は Vue の一般的な流儀ではありません、通常は次に説明する方法でイベントハンドラを記述します
        + 動作の理解のために説明しました

#### イベントハンドラを記述する(※Vueの流儀)

+ app.js の修正  
※以下のように修正します

```js
// データオブジェクト
var data = { message: "It works!" }

window.addEventListener("load", () => {
    // Vue インスタンスにオブジェクトを追加する
    var vm = new Vue({
        el: '#vue-app',
        data: data,
        // `methods` オブジェクトの下にメソッドを定義する
        methods: {
            getMessage() {
                this.message = 'Button Clicked!';
            }
        }
    })
});
```

+ WEBブラウザで確認します

```sh
http://localhost:8880/
※ [Call]ボタンを押すと Button Clicked! とWEBブラウザに表示されます
```

+ **＜ポイント＞**
    + 今回 `getMessage` メソッドは `function` を使用しない省略記法で記述しています
    + この記法だとイベントハンドラ内で `this` を参照可能です
    + ※ Vue の `data` オブジェクトの `message` プロパティを `this.message` で参照出来るのが奇妙に見えますが Vue の流儀なので慣れるしかないと思います

### <ここまでのまとめ>
+ このようにシンプルかつ最小限の方法で WEBコンテンツに Vue.js を導入することが出来ます
+ Vue.js を使用することによりWEBコンテンツの `ビュー定義` と `実装コード` を分離することが可能となります
+ また様々な方法で javascript からオブジェクト変数値を直接操作しても Vue のリアクティブ処理が破綻しないことが分かります

---
## Ajax リクエスト処理の追加  
+ HTMLファイルに Ajax リクエスト処理を追加します (axios "アクシオス" 使用)

+ API の仕様

|No|URL|data 要素|データ型|内容|
|:--|:--|:--|:--|:--|
|1|http://localhost:8881/api/message|massage|文字列|メッセージを返します|
|2|http://localhost:8881/api/message|error|bool値|APIのエラー状況を返します|

+ 取得する JSON の例

```json
{
    "message": "some message",
    "error": false
}
```

+ index.html の修正  
※以下のように修正します

```html
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <script src="https://cdn.jsdelivr.net/npm/vue@2.6.12"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <script src="/scripts/app.js" language="javascript" type="text/javascript"></script>
    <title>index</title>
  </head>
  <body>
    <div id="vue-app">
      <div>{{ message }}</div>
      <button v-on:click="getMessage">Call</button>
    </div>
  </body>
</html>
```

+ app.jsの修正  
※以下のように修正します

```js
// データオブジェクト
var data = { message: "It works!" }

window.addEventListener("load", () => {
    // Vue インスタンスにオブジェクトを追加する
    var vm = new Vue({
        el: '#vue-app',
        data: data,
        // `methods` オブジェクトの下にメソッドを定義する
        methods: {
            getMessage() {
                // バックエンドサーバーへ Ajax リクエスト
                axios.get(
                    'http://localhost:8881/api/message', {
                        headers: {'content-type': 'application/json'}
                    })
                    .then(response => {
                        // Ajax リクエストの戻り値を取得
                        this.message = response.data.message;
                    })
                    .catch(error => {
                        // エラーメッセージを表示
                        this.message = error;
                    });
            }
        }
    })
});
```

+ WEBブラウザで確認します

```sh
http://localhost:8880/
※ [Call]ボタンを押すと Error: Network Error とWEBブラウザに表示されます
※ API リクエストを受けるバックエンドサーバーの準備が出来ていないからです
```

+ **＜ポイント＞**
    + `getMessage` メソッドから Ajax通信で `http://localhost:8881/api/message` をリクエストしています
    + エラーが発生した場合には `message` に内容を表示します

### <ここまでのまとめ>
+ axios ライブラリを使用してバックエンドサーバーに API リクエストを投げることが出来そうです
+ 今回の例では厳密な REST API ではありません、単に HTTP リクエスト GET で JSON を取得します

---
## バックエンドサーバーの構築

+ 修正するファイルの概要

|No|ファイルPATH|拡張子|記述言語|概要・内容|
|:--|:--|:--|:--|:--|
|1|docker-compose.yml|yml|YML|Docker 起動|

#### Jetty コンテナの追加  
+ Jetty は JVM系言語の WEBアプリをデプロイする WEBサーバーです
    + ここでは 8881 ポートで起動させます

+ docker-compose.yml の修正  
※以下のようにファイルを修正します

```yml
version: '3'
services:
  nginx:
    image: nginx:latest
    ports:
      - "8880:80"
    volumes:
      - ./var/www/html:/usr/share/nginx/html
    container_name: nginx01

  jetty:
    image: jetty:9.4.38
    ports:
      - "8881:8080"
    volumes:
      - ./target:/var/lib/jetty/webapps
    container_name: jetty01
```

+ Docker Compose を実行します

```sh
※アプリディレクトリで
$ docker-compose up -d
Creating jetty01 ... done
```

+ WEBブラウザで確認します

```sh
http://localhost:8881/
※ Error 404 - Not Found. とWEBブラウザに表示されますが Jetty コンテナは立ち上がっています
```

### <ここまでのまとめ>
+ JVM系 WEBアプリ実行環境 Jetty も `docker-compose.yml` の設定だけでコンテナが立ち上がります

---
## Java WEBアプリの作成
+ JVM系のWEBアプリは通常 拡張子 `.war` 形式の zipファイルとして作成されます
+ Jetty の場合 `/var/lib/jetty/webapps` ディレクトリに `*.war` ファイルを配置することで自動的にデプロイされます
+ ローカルPCの `*.war` WEBアプリを `docker-compose.yml` の `jetty` の `volumes` 設定でコンテナに配置します
    + ここでは `./target` ディレクトリに `.war` ファイルをビルド出力すれば、自動的にコンテナにデプロイされます

### war アプリの作成

+ 作成するファイルの概要

|No|ファイルPATH|拡張子|記述言語|概要・内容|
|:--|:--|:--|:--|:--|
|1|pom.xml|xml|XML|Maven ビルド設定|
|2|src\main\webapp\WEB-INF\web.xml|xml|XML|サーブレット定義|
|3|src\main\webapp\WEB-INF\spring\app-config.xml|xml|XML|Spring bean定義|
|4|src\main\webapp\WEB-INF\spring\mvc-config.xml|xml|XML|Spring bean定義|
|5|src\main\java\org\example\controller\ApiController.java|java|Java|コントローラクラス|
|6|src\main\java\org\example\response\Response.java|java|Java|レスポンスクラス|

+ 必要なファイルをまとめて作成します

```sh
※アプリディレクトリで
$ mkdir -p src/main/webapp/WEB-INF/spring
$ mkdir -p src/main/java/org/example/controller
$ mkdir -p src/main/java/org/example/response
$ touch pom.xml
$ touch src/main/webapp/WEB-INF/web.xml
$ touch src/main/webapp/WEB-INF/spring/app-config.xml
$ touch src/main/webapp/WEB-INF/spring/mvc-config.xml
$ touch src/main/java/org/example/controller/ApiController.java
$ touch src/main/java/org/example/response/Response.java
```

#### xml 設定ファイルの作成

##### Maven の設定

+ Maven ビルド設定である pom.xml の作成  
※以下のようにファイルを作成します

```sh
※アプリディレクトリ/pom.xml
```

+ pom.xml の内容

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>org.example</groupId>
    <artifactId>minimum-api</artifactId>
    <packaging>war</packaging>
    <version>1.0.0</version>
    <name>webapp</name>
    <properties>
        <jackson.version>2.11.0</jackson.version>
        <spring.version>5.2.6.RELEASE</spring.version>
        <lombok.version>1.18.12</lombok.version>
        <java.version>1.8</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <dependencies>
        <!-- Jackson -->
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>${jackson.version}</version>
        </dependency>
        <!-- Spring -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>${lombok.version}</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
    <pluginRepositories>
        <pluginRepository>
            <id>central</id>
            <name>Central Repository</name>
            <url>https://repo.maven.apache.org/maven2</url>
            <layout>default</layout>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
            <releases>
                <updatePolicy>never</updatePolicy>
            </releases>
        </pluginRepository>
    </pluginRepositories>
    <repositories>
        <repository>
            <id>central</id>
            <name>Central Repository</name>
            <url>https://repo.maven.apache.org/maven2</url>
            <layout>default</layout>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>
    <build>
        <finalName>api</finalName>
        <plugins>
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                    <encoding>${project.build.sourceEncoding}</encoding>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

+ **＜ポイント＞**
    + `dependencies` セクションで Jackson(JSOMマッパー), Spring, Lombok の jarライブラリをダウンロードします
    + `pluginRepositories`, `repositories` セクションで Maven Central Repository の設定を追加します(※HTTPS対策)
    + `build` セクションで .war ファイルへビルドする設定を追加します
        + `<finalName>api</finalName>` と設定することにより `api.war` というファイル名で `./target` ディレクトリに出力されます
        + `$ mvn install` コマンドでビルドします (※maven-compiler-plugin が良きに実行してくれます)
        + `./target` ディレクトリに `api.war` が出力されることにより Docker Jetty コンテナ側の `/var/lib/jetty/webapps` にコピーされ自動的に Docker コンテナ側の war アプリも更新されます
            + ※実稼働を想定する場合にはこの方法が唯一のデプロイ方法ではないです

##### Servlet の設定
+ Servlet とは Java で HTTPリクエスト・レスポンスを扱う Java EE の標準仕様の一部分です
    + `Jetty` や `Tomcat` は Servlet API の標準仕様を実装した Java の WEBサーバーです
        + `Jetty` や `Tomcat` は Servlet API の実装なので RDBMS へアクセスする機能などはありません
        + RDBMS へアクセスする場合には JPA という標準仕様があり `Hibernate` などが JPA を実装します
    + Java EE 標準仕様のリファレンス実装として全部入りの `GlassFish` というアプリケーションサーバソフトがあります

+ Servlet の設定である web.xml の作成  
※以下のようにファイルを作成します

```sh
※アプリディレクトリ/src/main/webapp/WEB-INF/web.xml
```

+ web.xml の内容

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app__3__1.xsd" version="3.1">
    <display-name>minimum-api</display-name>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>/WEB-INF/spring/app-config.xml</param-value>
    </context-param>
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <servlet>
        <servlet-name>dispatcher</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>/WEB-INF/spring/mvc-config.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>dispatcher</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

+ **＜ポイント＞**
    + `org.springframework.web.servlet.DispatcherServlet` が Spring Framework として動作する `Servlet` になります
       + Spring Framework では基本的に全てのリクエスト・レスポンスが `DispatcherServlet` で行われます
       + また Spring Framework とは関係ない独自の `Servlet` を `web.xml` に設定することも可能です
          + 超レガシーな Java WEB システムでは1ページのHTMLに一つの `Servlet` が設定されたケースもあると思います

#### Spring の設定

+ Spring の設定である app-config.xml, mvc-config.xml の作成  
※以下のようにファイルを作成します

```sh
※アプリディレクトリ/src/main/webapp/WEB-INF/spring/app-config.xml
※アプリディレクトリ/src/main/webapp/WEB-INF/spring/mvc-config.xml
```

+ app-config.xml の内容

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="org.example"/>
</beans>
```

+ mvc-config.xml の内容

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:context="http://www.springframework.org/schema/context"
    xmlns:mvc="http://www.springframework.org/schema/mvc"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <context:component-scan base-package="org.example"/>
    <mvc:annotation-driven />
</beans>
```

+ **＜ポイント＞**
    + `component-scan base-package` で `org.example` パッケージのアノテーション設定を読み込んでいます
    + `mvc:annotation-driven` で Spring MVC におけるアノテーション設定による動作を有効にしています
    + この例では `app-config.xml`, `mvc-config.xml` の二つの XML ファイルを設定しています
        + 用途によって分けていますが、必ずしもこの方法でないと設定が読み込めないわけではないです

#### java コードの作成
+ コントローラクラスとレスポンスクラスを作成します  
※以下のようにファイルを作成します

```sh
※アプリディレクトリ/src/main/java/org/example/controller/ApiController.java
※アプリディレクトリ/src/main/java/org/example/response/Response.java
```

##### コントローラー POJO クラスの実装

+ ApiController.java の内容

```java
package org.example.controller;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.example.response.Response;

/**
 * Ajax通信で HTTP リクエストされる Controller POJO クラス
 */
@RequiredArgsConstructor
@Controller
public class ApiController {

    ///////////////////////////////////////////////////////////////////////////
    // Field

    @NonNull
    private ApplicationContext context; // lombok が自動でコンストラクタをつくりそこからインジェクションされます

    ///////////////////////////////////////////////////////////////////////////
    // public methods

    // Ajax リクエストを受けメッセージを返します
    @CrossOrigin
    @RequestMapping(
        value="/message",
        method=RequestMethod.GET,
        headers="Accept=application/json"
    )
    public @ResponseBody Response getMessage() {
        // 本来ならここでビジネスロジックを処理して

        // レスポンス用のオブジェクトを返す
        return (Response) context.getBean(
            "response", // bean の名前を指定
            "Did you call me?", // メッセージの内容
            false
        );
    }
}
```

+ **＜ポイント＞**
    + `@RequiredArgsConstructor` アノテーションで lombok が自動で必要なコンストラクタをコンパイル時に自動生成します
    + `@Controller` アノテーションで Spring MVC のコントローラとして動作します
    + `@CrossOrigin` アノテーションで他サイトからのアクセスを許可します(※Cross-Origin Resource Sharing)
    + `@RequestMapping` アノテーションで HTTPリクエストを受けます
    + `@ResponseBody` アノテーションで HTTPレスポンスを返します
        + この例では `Jackson` ライブラリで POJO を JSON にマッピングしています

##### レスポンス POJO クラスの実装

+ Response.java の内容

```java
package org.example.response;

import lombok.Getter;
import lombok.NonNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * JSON にマッピングされる POJO クラス
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Component(value="response") // bean の名前
@Scope(value="prototype")
public class Response {

    ///////////////////////////////////////////////////////////////////////////
    // Field

    @NonNull
    private String message; // メッセージの内容

    @NonNull
    private boolean isError; // エラーが発生したかどうか(※ダミー)
}
```

+ **＜ポイント＞**
    + `@Getter`, `@AllArgsConstructor` アノテーション等は lombok が自動で必要なメソッドをコンパイル時に自動生成します
        + lombok ライブラリを使用することでボイラーコードの記述を回避でき、見通しの良いコードを書くことが可能です
    + `@Component` アノテーションで bean 名 `response` として Spring Framework に登録します
    + `@Scope` アノテーションで `prototype` と指定することにより DI から new 相当でオブジェクトを取得します
        + ※ Spring Framework のデフォルト動作では DI で取得するオブジェクトはシングルトンです

#### java WEBアプリのビルド
+ ビルドツールとして Maven (メイブン) を使用します
    + 基本的に `pom.xml` が存在するディレクトリで `mvn install` コマンドを走らせるとJavaアプリがビルドされます

+ ビルドコマンド を実行します

```sh
※アプリディレクトリで
$ mvn install
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  4.041 s
[INFO] Finished at: 2021-03-21T09:23:45+09:00
[INFO] ------------------------------------------------------------------------
```

+ WEBブラウザで確認します

```sh
http://localhost:8880/index.html
※ [Call]ボタンを押すと Did you call me? とWEBブラウザに表示されます
```

### <ここまでのまとめ>
+ フロント側のWEBコンテンツから、バックエンドにAPIリクエストすることが出来ました
+ またバックエンド側は、フロントのAPIリクエストに応答することが出来ました

---
## CSS フレームワークを適用
+ CSS フレームワークとして UIkit を使用します

+ index.html の修正  
※以下のようにファイルを修正します

```html
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <script src="https://cdn.jsdelivr.net/npm/vue@2.6.12"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/uikit@3.6.18/dist/css/uikit.min.css" />
    <script src="https://cdn.jsdelivr.net/npm/uikit@3.6.18/dist/js/uikit.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/uikit@3.6.18/dist/js/uikit-icons.min.js"></script>
    <script src="/scripts/app.js" language="javascript" type="text/javascript"></script>
    <title>index</title>
  </head>
  <body>
    <div id="vue-app">
      <div class="uk-section uk-section-small uk-section-secondary"></div>
        <div class="uk-section uk-section-primary">
          <div class="uk-container uk-container-small">
            <h1 class="uk-heading-large">Message</h1>
            <p>
              <span uk-icon="icon: info"></span>
              <span class="uk-text-lead">{{ message }}</span>
            </p>
            <p>
              <button class="uk-button uk-button-large uk-button-primary" v-on:click="getMessage">Call</button>
            </p>
          </div>
        </div>
      <div class="uk-section uk-section-xlarge uk-section-muted"></div>
    </div>
  </body>
</html>
```

+ WEBブラウザで確認します

```sh
http://localhost:8880/index.html
```

### <ここまでのまとめ>
+ CSS フレームワークの導入で見た目を整えることが出来ます

---
## 感想
+ バックエンド側の実装を、フロント側からのAPIリクエストに対するJSON応答に限定することにより、システムが疎結合に保たれると思いました
+ バックエンド側で JSP(HTML) のようなフロント部分を提供しない仕組みは、フロント側がスマホネイティブアプリや Unity ゲームなどの場合にはそれらに対して汎用的に利用可能となると思いました

---
## 今後の展開は？
+ フロント側の javascript を TypeScript に置き換えると良いかも知れません
    + webpack の設定が必要になります
+ バックエンド側の処理に RDBMS を使用する設定を追加すると良いと思います
    + Java を使用する理由の一つに RDBMS に対する処理が実用的に書けることがあります
    + JPA 仕様を実装する `Hibernate` 等のライブラリが必要となりますが `pom.xml` Jetty コンテナに同梱出来ます
    + この場合に必要となるのは `MySQL`, `PostgreSQL` の Docker コンテナとなります

---
## ありがとうございました