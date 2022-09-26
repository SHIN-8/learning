---
html:
  embed_local_images: true
  embed_svg: true
  offline: false
  toc: true

print_background: false
---

## アジェンダ
* CodeBuildとは？
* CodeBuildの仕組み
* ハンズオン

## CodeBuildとは？
AWSで提供されるCode系のサービス群の一つです  
CI/CDに関しては、CodeCommit/CodeBuild/CodeDeploy/CodePipelineがあります  

|サービス|概要|
|---|---|
|CodeCommit|GitLab同様のGitリポジトリ|
|CodeBuild|ビルドするサービス|
|CodeDeploy|デプロイするサービス|
|CodePipeline|ソースコード変更の検出、ビルド、デプロイといった一連の流れを定義できます|

これらを利用すると、ソースコードの管理からデプロイまでの自動化をAWS上で行うことができます  
サーバーの管理いらずに！！

## CodeBuild
* 全体的な流れ
    * プロジェクトの作成
    * エントリポイントから実行
        * AWSマネジメントコンソール
        * CodePipelineからの連携
        * AWS CLI
        * AWS SDK
    * ビルド用コンテナの起動
        * 予めAWSがイメージを用意してくれている
        * 自前で作成したイメージも使える
        * ソースコードもダウンロードされている
    * buildspecの実行
        * ソースコード内にファイルを作成
        * マネジメントコンソールから入力
    * 成果物(artifact)の出力
        * S3
* ビルド用コンテナ一覧
    * .Net Core
    * Android
    * Docker
    * Golang
    * Java
    * Node.js
    * PHP
    * Python
    * Ruby

## ハンズオンの目標
CodeCommitに変更をpushするとcomposerで依存関係を解決し、  
そのまま動作可能なdockerイメージの作成、およびECRへの保存をする  

## ハンズオンの流れ
* DockerコンテナからLaravelプロジェクトをマウントして動作させる
* Laravelプロジェクトを含むDockerイメージを作成して動作させる
* CodeCommitにリポジトリを作成し、プロジェクトをpushする
* CodeCommitへのPush時、DockerイメージをビルドしてECRへ保存する
* ECRに保存したDockerイメージからDockerコンテナを立ち上げ動作を確認

## ハンズオンの手順
それではハンズオンを始めます

### プロジェクトの作成

まずはLaravelプロジェクトを作成します  
```
composer create-project --prefer-dist laravel/laravel プロジェクト名
```

作成したプロジェクトが動くことを確認します  
```
#デフォルトだと8000番ポートでLISTENします
php artisan serve --port 8000
```

次にこのプロジェクトをDockerコンテナ上で動かします  
先程のserveコマンドは止めてください  
まずはプロジェクトのルートにDockerfileを作成します  
serveコマンドにhostオプションを付けることによって外部からのアクセスを許可しています  
```dockerfile
FROM php:7.2-cli
WORKDIR /usr/src/myapp
CMD ["php", "artisan", "serve", "--host", "0.0.0.0"]
```

Dockerイメージをビルドします
```
docker build -t codebuild_test:latest .
```

Dockerコンテナを立ち上げます  
ポートは8000番でフォワーディングしています
```
docker run -v プロジェクトへのパス:/usr/src/myapp -p 8000:8000 codebuild_test:latest
```

8000番にアクセス出来るはずです  
これでコンテナ内でLaravelプロジェクトを動作させることができました  
次にコンテナ起動時にプロジェクトをマウントするのではなく、Dockerイメージ内にプロジェクトを含めます  
Dockerfileを修正します
```dockerfile
FROM php:7.2-cli
#追加
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
CMD ["php", "artisan", "serve", "--host", "0.0.0.0"]
```

もう一度ビルドします
```
docker build -t codebuild_test:latest .
```

コンテナを立ち上げます
今回は先程と違い、Dockerイメージにプロジェクトが含まれているので、マウントが不要です
※先程のコンテナが残っている場合は削除してください
```
docker run -p 8000:8000 codebuild_test:latest
```

8000番にアクセス出来ることが確認できます  

### AWS検証用アカウントでログイン
http://knowledge.core.king/projects/exclusively-system/wiki/AWS-Sub  
上記URLのDeveloperユーザーをご利用ください  

### CodeCommitの設定
次にこのプロジェクトをCodeCommitへ保存します  
まず、AWSマネジメントコンソールからCodeCommitのページを開いてください  
「リポジトリの作成」ボタンからリポジトリを作成します  
※他の方と区別する為に、ご自身の識別子を含めてください  
「URLのクローン」から「HTTPSのクローン」を選択し、URLをコピーしてください  
通常であれば認証情報を発行しますが、今回は事前に用意した認証情報とあわせてリポジトリにPushしてください  
※今回はやりませんが「IAMユーザーの詳細画面->認証情報->AWS CodeCommitのHTTPS Git認証情報->生成」から出来ます  

### CodeBuildプロジェクトの作成
ここまでで、「プロジェクトをDockerイメージとして保存」・「CodeCommitへのPush」ができました  
いよいよCodeBuildの設定をします  
AWSマネジメントコンソールからCodeBuildのページを開いてください  
左ペインから「ビルドプロジェクト」を選択します  
「Create build project」ボタンを押します  
以下画像のように進めてください  
![img1](.\CodeBuild設定1.JPG "img1")  
![img2](.\CodeBuild設定2.JPG "img2")  
![img3](.\CodeBuild設定3.JPG "img3")  
![img4](.\CodeBuild設定4.JPG "img4")  
Sourceセクションの「Source provider」には「Code Commit」を指定しています  
「リポジトリ」には先程作成したリポジトリを指定してください  
これによりビルド開始時に、ビルド用コンテナ内でリポジトリをクローンしてくれます  
また、ビルド用コンテナにPHP runtimeを指定していることで、ビルド用コンテナ内でcomposerが利用できます  
BuildSpecとはビルド用コンテナ内で実行するコマンドです  
リポジトリにファイルを作るか、マネジメントコンソールで入力するかを選べます(今回はマネジメントコンソールからの入力)  
一先ずはpwdコマンドを入力してください(実際のビルド用コマンドは後から追加します)  
artifactを指定すると、ビルド後の成果物を保存してくれます  
※今回はビルド内でECRへのプッシュを行うので利用しません  
これでビルドプロジェクトが作成できました  
「Start build」でビルドしてみてください  
「Build logs」でpwdコマンドが実行されたはずです  
ビルド用コンテナ内について調べる為に、buildspecを変更します  
「編集」ボタンから「Buildspec」を選択し修正します  

```yaml
version: 0.2

phases:
  build:
    commands:
       - pwd
       - ls
       - php -v
       - docker -v
       - composer -v
```

実行すると以下のことが分かります  

* カレントディレクトリがプロジェクト内
* phpが入っている
* dockerが入っている
* composerが入っている

### ECRの設定
このハンズオンでの目標はCodeBuildでビルドを行い、DockerイメージをECRに保存することです  
その為のDockerリポジトリをECRに作成します  
AWSマネジメントコンソールからECRのページを開いてください  
「リポジトリ作成」ボタンからリポジトリを作成します  
作成したリポジトリのURIは、この後の作業で使用します  

### CodeBuildプロジェクトの設定
CodeBuildのページに戻ります  
composerによるビルドと、dockerによるビルドを行う為にBuildSpecを修正します  

```yaml
version: 0.2

phases:
  pre_build:
    commands:
      - $(aws ecr get-login --no-include-email --region us-west-2)
  build:
    commands:
       - composer install
       - docker build -t 597671767797.dkr.ecr.us-west-2.amazonaws.com/test_mizuyabu:latest .
       - docker push 597671767797.dkr.ecr.us-west-2.amazonaws.com/test_mizuyabu:latest
```

これでCodeBuildによるphpプロジェクトのビルドが出来ました  
冒頭にお話しした通りCodePipelineを利用すると、ソースコードの変更を検知して、ビルド・デプロイまで自動で行うことができます  