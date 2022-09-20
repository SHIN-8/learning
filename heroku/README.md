---
marp: true
paginate: true

---
# 勉強会: Heroku を使用してみる
2021-08-18  
株式会社キングプリンターズ システム課

---
## 必要なツール

+ git
+ npm

```sh
> git --version
git version 2.27.0.windows.1
> node -v
v12.16.3
> npm -v
6.14.4
```

※バージョンの詳細は違っていても大丈夫だと思います

---
## Heroku とは？

> 米国Heroku(Heroku社)[※セールスフォース・ドットコムに買収された]が提供するPaaS（Platform as a Service）の名称

---
## Heroku の特徴その①

+ 毎月の無料 dyno 時間を使用してアプリケーションを無料で実行できます
+ 未認証アカウントの場合、無料 dyno 時間は 550 時間分までとなります
+ クレジッドカード番号を登録すると認証済みアカウントとなり、無料 dyno 時間は 1000 時間分までとなります

---
## 何が Heroku に向くか？

+ 個人開発者がプロトタイプアプリをシンプルに試したい時に有用だと思います

---
## アカウントの作成

+ URL  
https://signup.heroku.com/jp

+ アカウント情報

```sh
苗字          :Adachi
名前          :Hiroyuki
メールアドレス:h_adachi@kingprinters.com
役職          :職業開発者
国            :日本
```

---
+ アカウントの確認

> お客様のメール (h_adachi@kingprinters.com) で
アカウントの確認をしてください。

+ パスワードの設定

```sh
xxxxxnnnn!
```

---
## Heroku アプリの新規作成

+ URL  
https://dashboard.heroku.com/apps

+ Heroku WEBダッシュボードからアプリ作成  

#### [Create new app] を押す

```sh
App name       :heroku-app-321
Choose a region:United States
```

#### [Create app] を押す

---
## Heroku アプリの表示

#### [Open app] を押す  

※例
```sh
https://heroku-app-321.herokuapp.com/
```

※上記URLでアプリにアクセス出来ます  
※中身はまだ何もありません

---
## Heroku CLI のインストール

+ URL  
https://devcenter.heroku.com/articles/heroku-cli

※各自の環境のコマンドラインツールをインスト―スします

+ Windows 10 では以下のディレクトリにインストーラでインストールしました  

```sh
C:\Program Files\heroku
```

---
## Heroku CLI で Heroku にログインしてみる

```sh
> heroku login
```

※WEBブラウザが立ち上がるので[Login]ボタンを押す

```sh
※省略
Logging in... done
Logged in as h_adachi@kingprinters.com
```

ログイン出来ました

---
## Heroku の特徴その②

+ Heroku はアプリのデプロイ操作を git から行うことが出来ます※他の方法もあります
    + Heroku を利用することにより git 操作が身につきます
+ また GitHub のプロジェクトと連携し GitHub に push すると Heroku に自動デプロイすることが出来ます

---
## Heroku アプリに git を紐づける  

※ここではデスクトップに `heroku-app-321` フォルダを作成しました  

+ git の初期化と Heroku アプリへの紐づけ

```sh
> cd C:\Users\kpu0560\Desktop\heroku-app-321
> git init
> heroku git:remote -a heroku-app-321
```

※この git ディレクトリが Heroku アプリと紐づきました

---
## git のリモートURLを確認

```sh
> git remote -v
heroku  https://git.heroku.com/heroku-app-321.git (fetch)
heroku  https://git.heroku.com/heroku-app-321.git (push)
```

※この git のリモートURLが `heroku` として設定される

---
## ちなみにシナジーのリモートURLを表示してみる

```sh
> cd C:\docker\kp\Source
> git remote -v
origin  https://gitlab.kingprinters.com/Shared/KP.git (fetch)
origin  https://gitlab.kingprinters.com/Shared/KP.git (push)
```

※通常は git のリモートURLは `origin` ですが Heroku ではリモートURLが `heroku` となります

---
## Heroku がサポートする言語について

|言語|依存関連マネージャ|依存関連ファイル|
|:--|:--|:--|
|Node.js|Node|package.json|
|Ruby|bundler|Gemfile|
|Python|Pip|requirements.txt|
|Java|Maven|pom.xml|
|PHP|Composer|composer.json|
|Go|Go|go.mod|
|Scala|sbt|build.sbt|
|Clojure|Leiningen|project.clj|

---
## Heroku の注意点

+ Heroku アプリはファイルを出力することが出来ません！
    + 永続的な保存が必要な場合、RDBなどのストレージが必要となります
        + Heroku が用意する PostgreSQL アドオンなどを使用することが出来ます

---
## 最小限の Node.js + Express アプリの作成

#### Express とは？

> [Express とは Node.js のための高速で、革新的な、最小限のWebフレームワークです](http://expressjs.com/)

---
+ アプリディレクトリで npm プロジェクトを初期化します

```sh
> cd ※アプリディレクトリ
> npm init
※全てそのままエンターで yes を入力します
```

+ 最小限の index.js を作成します ※アプリディレクトリに作成

```js
const express = require('express');
const app = express();
const port = process.env.PORT || 8080;

app.get('/', (req, res) => { res.send('Hello, World'); });
app.listen(port, () => { console.log(`Express Server Listen START at port=${port}`); });
```

---
+ Procfile ファイルを作成します(※Heroku の起動用ファイル) ※アプリディレクトリに作成

```sh
web: node index
```

+ npm で express をインストールします

```sh
> npm i -S express
```

---
## Git で Heroku にアプリをプッシュする

+ Git でコミットして Heroku にプッシュする

```sh
> git add .
> git commit -am "init"
> git push heroku master
```

+ アプリにアクセスする

```sh
> heroku open
```

+ WEBブラウザにエラーページが表示された場合ログを確認することが出来ます

```sh
> heroku logs --tail
```

---
## ここまでのまとめ

+ Heroku に最小限の Node.js + Express アプリをデプロイ表示することが出来ました

---
## Express を発展させてみます

#### 静的ファイルを表示させてみます

+ index.js を以下のように修正します

```js
const express = require('express');
const app = express();
const port = process.env.PORT || 8080;
const path = require('path');

app.use(express.static(path.join(__dirname, 'public')));
app.listen(port, () => { console.log(`Express Server Listen START at port=${port}`); });
```

---
+ public/index.html を作成します

```html
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>Document</title>
</head>
<body>
  <h1>こんにちは世界</h1>
</body>
</html>
```

---
+ Git でコミットして Heroku にプッシュする

```sh
> git add .
> git commit -am "init"
> git push heroku master
```

+ Heroku アプリにアクセスします  

```sh
> heroku open
```

---
## ここまでのまとめ

+ Express は javascript のバックエンドのフレームワークでそれ自体が `WEBサーバ` となります

---
## さらに改造してみます

#### 実は Express はWEBサーバとしてローカル起動が可能です

+ 以下のコマンドでローカル起動する [※終了は Ctrl + C] 

```
> node index.js
```

+ http://localhost:8080/ でアクセス可能です

---
#### フロントエンドに Vue を使ってみる

+ public/scripts/app.js を作成します

```sh
var data = { message: "It works!" };
window.addEventListener("load", () => {
    var vm = new Vue({
        el: '#vue-app',
        data: data
    })
});
```

---
+ public/index.html を修正します

```html
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

---
+ Git でコミットして Heroku にプッシュする

```sh
> git add .
> git commit -am "init"
> git push heroku master
```

+ Heroku アプリにアクセスします  

```sh
> heroku open
```

It works! と表示されれば Vue が動作しています

---
## まとめ

+ Heroku で Vue + Express の最小構成のWEBアプリケーションを立ち上げることが出来ました
+ また Express フレームワークもシンプルな構成でいろいろ出来そうなので有用かと思いました

---
## 作業メモ

+ Heroku からアプリのソースコードを git でクローンする

```sh
> cd C:\Users\kpu0560\Desktop
> git clone https://git.heroku.com/heroku-app-321.git
> cd heroku-app-321
```

+ リモートURL確認

```sh
> git remote -v
origin  https://git.heroku.com/heroku-app-321.git (fetch)
origin  https://git.heroku.com/heroku-app-321.git (push)
```

+ リモートURLの名前を変更※origin から heroku

```sh
> git remote rename origin heroku
```

<style>
table, th, td {
    font-size: 95%;
}
</style>