## 環境
* aws-cli 1.16

## 前提
今回のハンズオンを進めるにあたって、事前に以下のインストールをお願いします。  

### aws-cli
[Windows用インストール手順](https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/install-windows.html)  
[Mac用インストール手順](https://docs.aws.amazon.com/ja_jp/cli/latest/userguide/install-macos.html)  

## はじめに
今回はAWS CloudFormationについて、ハンズオン形式でご紹介します。  

## アジェンダ
* CloudFormationとは
* EC2インスタンスの起動
* EC2+ELBの構築
* ステージ毎の切り替え

## CloudFormation

### 概要
AWSリソースの作成や管理をしてくれるAWSサービスです。  
いわゆるInfrastracture as Codeなことができます。  
例えばEC2インスタンスを作る時、マネジメントコンソールからポチポチしてることを、テンプレートとコマンドでできます。  
具体的なユースケースとしては  

* 必要なリソースをテンプレートに記述し、リソースの新規作成
* テンプレートを修正し、実際の影響範囲を確認してから反映
* 関連するリソースの一括削除

みたいなことができます。  
以下のメリットがあります。

* 同じ環境を再現できる
    * ミスが起きにくい
    * 構築に時間がかからない
* バージョン管理できる
* 環境の削除も簡単

### 用語
* テンプレート
    * 管理するAWSリソースを定義
        * EC2を
        * このインスタンスタイプで
        * このVPCに
    * マネジメントコンソールでポチポチしてたことをここに記載
    * yamlとjsonが選べます
* チェンジセット
    * テンプレートから作成される、現在のスタックとの差分
    * リソース毎に作成・更新・削除のような変更内容が分かる
* スタック
    * チェンジセットを反映した起動しているリソースの集合
    * 例えばテンプレートで、EC2・RDS・VPCを記載してるなら、それらで一つのスタックとなる
    * 作成・変更・削除はスタック単位で行う

### ハンズオン～単純なEC2インスタンスの起動～
初めに単純なEC2インスタンスの起動をやってみます。  
以下のテンプレートを作成してください。  

```yml
AWSTemplateFormatVersion: 2010-09-09
Resources:
  SampleInstance:
    Type: 'AWS::EC2::Instance'
    Properties:
      InstanceType: "t2.nano"
      ImageId: "ami-0734444997c8c5902"
      SecurityGroups:
        - !Ref SampleSG
  SampleSG:
    Type: AWS::EC2::SecurityGroup
    Properties:
      GroupDescription: "for handson"
      SecurityGroupIngress:
        - CidrIp: "113.37.168.66/32"
          FromPort: "80"
          ToPort: "80"
          IpProtocol: "tcp"
Outputs:
  InstanceID:
    Value: !GetAtt SampleInstance.PublicIp
```

その他のEC2インスタンスに関するプロパティは下記で確認できます  
https://docs.aws.amazon.com/ja_jp/AWSCloudFormation/latest/UserGuide/aws-properties-ec2-instance.html  
「!Ref SampleSG」ですが、!RefはCloudFormationで利用できる関数の一つです  
引数に指定するリソースの種類によって返す値は変わりますが、セキュリティグループの場合は物理IDを返します  
リソース毎の戻り値は下記ページ下部にて確認できます  
https://docs.aws.amazon.com/ja_jp/AWSCloudFormation/latest/UserGuide/intrinsic-function-reference-ref.html  
「!GetAtt SampleInstance.PublicIp」の!GetAttも関数です  
リソースに関する値を取得します  
https://docs.aws.amazon.com/ja_jp/AWSCloudFormation/latest/UserGuide/intrinsic-function-reference-getatt.html  
templateが正しいことを以下のコマンドで検証できます  

```
aws cloudformation validate-template --template-body=file://ファイルパス
```

それではtemplateからスタックを作成します  

```
aws cloudformation deploy --stack-name スタック名 --template-file=テンプレートファイル名
```

「Successfully created/updated」と表示されれば成功です  
「マネジメントコンソール > CloudFormation > スタック」から以下のことを確認しましょう  

* 指定したスタック名で作成がされたこと
* リソースにEC2インスタンスがあること
* 「変更セット > 最後に実行された変更セット」にて、EC2インスタンスがadd(新規作成)されたこと
* 「出力」にて、IPアドレスを確認し、httpでアクセスができること

### ハンズオン～EC2 + ELB～
それでは次に、先程のスタックをEC2インスタンスとELBの構成に変更します  
ELBは設定項目が少ないClassic Load Balancerを使用します  
以下のテンプレートを作成してください  

```yml
AWSTemplateFormatVersion: 2010-09-09
Resources:
  SampleInstance:
    Type: "AWS::EC2::Instance"
    Properties:
      InstanceType: "t2.nano"
      ImageId: "ami-0734444997c8c5902"
      SecurityGroups:
        - !Ref SampleInstanceSG
  SampleInstanceSG:
    Type: AWS::EC2::SecurityGroup
    Properties:
      GroupDescription: "for handson"
      SecurityGroupIngress:
        - CidrIp: "0.0.0.0/0"
          FromPort: "80"
          ToPort: "80"
          IpProtocol: "tcp"
  SampleELB:
    Type: "AWS::ElasticLoadBalancing::LoadBalancer"
    Properties:
      Instances:
        - !Ref SampleInstance
      Listeners:
        - LoadBalancerPort: 80
          InstancePort: 80
          Protocol: HTTP
      Subnets:
        - "subnet-83fc7bf5"
      SecurityGroups:
        - !GetAtt SampleELBSG.GroupId
  SampleELBSG:
    Type: AWS::EC2::SecurityGroup
    Properties:
      GroupDescription: "for handson"
      SecurityGroupIngress:
        - CidrIp: "113.37.168.66/32"
          FromPort: "80"
          ToPort: "80"
          IpProtocol: "tcp"
Outputs:
  ElbUrl:
    Value: !GetAtt SampleELB.DNSName
```

「!Ref SampleInstance」については、前述の通りでインスタンスの物理IDを返します  
今回は影響範囲を確認するために、チェンジセットを作成した後でそれを適用します  
まずはチェンジセットの作成です  

```
aws cloudformation create-change-set --stack-name スタック名 --change-set-name=add-elb --template-body=file://ファイルパス
```

次に作成したチェンジセットをマネジメントコンソールから確認してみます  
「CloudFormation > スタック > 変更セット」を開き、作成した変更セットを選択すると、今回の変更箇所の確認ができます  
ELBがAddされていることを確認してください  
次に変更セットを適用するために下記コマンドを実行します  

```
aws cloudformation execute-change-set --stack-name=スタック名 --change-set-name=add-elb
```

マネジメントコンソールにて、ELBが作成されたこと、ELBにインスタンスが追加されたこと、ELBのDNS名が出力されたことが確認できます  
DNS名にアクセスするとNginxのページが表示されることも確認してください。  

### ハンズオン～ステージ毎の切り替え～
次に、開発・本番でインスタンスタイプが変わるようにします  
テンプレートを下記に修正してください  

```yml
AWSTemplateFormatVersion: 2010-09-09
Parameters:
  Env:
    Type: String
    AllowedValues:
      - prd
      - stg
Mappings:
  EnvMap:
    prd:
      InstanceType: "t2.micro"
    stg:
      InstanceType: "t2.nano"
Resources:
  #EC2 Instance
  SampleInstance:
    Type: "AWS::EC2::Instance"
    Properties:
        InstanceType: !FindInMap [ EnvMap, !Ref Env, InstanceType ]
        #AmazonLinux2
        ImageId: "ami-0734444997c8c5902"
```

テンプレートについていくつか補足します  

```yml
Parameters:
  Env:
    Type: String
    AllowedValues:
      - prd
      - stg
```

Parametersセクションでは、スタック作成時にユーザーからの入力を受け付けます  
今回はユーザーにprdもしくはstgを入力させます  

```yml
Mappings:
  EnvMap:
    prd:
      InstanceType: "t2.micro"
    stg:
      InstanceType: "t2.nano"
```

Mappingsセクションでは値のマッピングを行います  
今回はユーザーが入力した環境とインスタンスタイプのマッピングを行います  

```yml
Properties:
    InstanceType: !FindInMap [ EnvMap, !Ref Env, InstanceType ]
```

!FindInMap関数では、Mappingsセクションに記載された値を取得します  
今回の場合は、Mappings->EnvMap->!Ref Env->InstanceTypeの値を取得します  
!Ref関数では、引数にParametersセクションのキーが与えられると、ユーザーからの入力を返します  
今回の場合は、prdもしくはstgを返します  

それでは下記のコマンドでスタックを作成します  

```yml
aws cloudformation deploy --stack-name スタック名 --parameter-overrides Env=prd --template-file=ファイル名
```

--parameter-overridesの引数を、Env=prdかEnv=stgに変えるとインスタンスタイプが切り替わることが分かります  
以上でハンズオンは終わりです。  
お疲れ様でした！  

### 機能
* Nested Stacks
  * テンプレートの共通化
  * スタックの親子関係が作れる
* Cross Stack References
  * 別のスタックの値を参照できる
  * それぞれでエクスポート・インポートする
* Macros
  * 処理の途中でLambda関数を実行できる
  * templateの置換も可能
* Dynamic References
  * SSMに登録されたデータを動的に参照できる
* AWS CDK
  * 汎用言語からテンプレートが生成できる
  * Java/JS/TS/.NETに対応
* Drift Detection
   * テンプレートとリソースの差分がわかる
* RollBackTrigers
   * スタック作成中に異常があった場合ロールバックできる
* Stack Sets
    * スタックを複数のアカウント・リージョンに展開できる
* CloudFormation Desginer
    * GUIでテンプレートを編集できる
* スタックの保護
* サンプルテンプレート
* --generate-cli-skeletonでaws cliの設定を書き出し、--cli-input-jsonで読み込むと、同じパラメータが使える
* Noifications
    * スタックの作成完了なんかをSNSへ通知できる