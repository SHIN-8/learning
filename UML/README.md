---
html:
  embed_local_images: true
  embed_svg: true
  offline: false
  toc: true

print_background: false
---

PlantUML
====

## UML とは
UML(Unified Modeling Language)は、日本語では統一モデリング言語と呼称されます。平たく言えば、 __ソフトウェアを図解するためのルール集__ です。UMLは、オブジェクト指向に基づいたソフトウェア開発の要求分析や設計などに使用されます。

## UMLの種類
ソフトウェアを図解するための切り口は、対象の構造に着眼するアプローチと、対象がどのように動くのかというアプローチが考えられます。
UMLはそのどちらも表現できてUMLで表現される図は、ソフトウェアの静的な構造を表現する構造図とソフトウェアの振る舞いを表現する振る舞い図に大きく二分できます。次に、UMLで表現できる図の一覧を示します。


|分類|名称||
|:----|:----|:----|
|構造図|クラス図||
||複合構造図||
||コンポーネント図||
||配置図||
||オブジェクト図||
||パッケージ図||
|振る舞い図|ユースケース図||
||アクティビティ図||
||ステートマシン図||
||シーケンス図||
||相互作用図||
||コミュニケーション図||
||タイミング図||

## UMLを取り巻くツール
UMLは、手でざっくり書くのもいいのですが、やはりドキュメントとして残すには、作図ツールを用いるのが良いです。作図ツールには、astah*communityやEnterprise Architect といったツールなどがありますが、保存形式が独自だったりPNGやJPEGへの出力が必要なため、メンテナンスの観点からソフトウェアの保守のたびにこれらのツールから画像出力というのはちょっと面倒です。

そこでPlantUMLです。PlantUMLはテキストベースの表記から図を生成することが可能で、dot(Graphviz)を使えば図の自動生成も可能です。またテキストベースですから、gitとの相性もよく、変更された部分の仕様を差分で確認できます。

## 書いてみる
ここからは実際にPlantUMLを使って作図をしていきます。

### ユースケース図
```plantuml

'actorキーワードで、アクターを定義。アユーザーや関連する外部システム
actor アクター as user

'関連するユースケースを一つにまとめる
rectangle "ユースケース" {
    usecase ユースケース名 as case
    usecase 拡張ユースケース as extended
    usecase 包含ユースケース as included
    
    user-case
    case-extended:<<extend>>
    case ..> included:<<include>>
}
```

### シーケンス図

```plantuml
'actorキーワードで、アクターを定義。アクターは利用者
actor アクター as user

'boundaryキーワードで、バウンダリを定義。バウンダリはUI
boundary  バウンダリ as boundary

'controlキーワードで、コントローラを定義。コントロールはコントローラー
control コントロール as controller

'entityキーワードで、エンティティを定義エンティティは実データ
entity エンティティ as database

'activateキーワードで生存線を活性化
activate user
user->boundary: コメントで関連の説明をかける

'refを使って別の図を参照
ref  over user, database
XXX図を参照
end ref

activate boundary
'altを使って条件分岐
alt 分岐条件
    boundary->>controller
        activate controller
            controller->database
            activate database
                database->database
                database-->controller
            deactivate database
            controller-->>boundary
        deactivate controller
    boundary-->user
else
    boundary-->user: 
end 
deactivate boundary



activate user
user->boundary
activate boundary
'loopで繰り返し使って条件分岐
loop 繰り返し条件 
    boundary->boundary: カウントアップなど
end 
boundary-->user
deactivate boundary
deactivate user

destroy user
destroy boundary
destroy controller
destroy database
hide footbox
```

### クラス図
- enum
```plantuml
'enum で列挙型
enum Enum {
    Value1
    Value2
}
```

- class

```plantuml
'class キーワードでクラスを定義
class クラスA {
    id: number
    + func1()
    # func2()
    - func3()
    ~ func4()
    {static} staticValue: number
}
```

- interface 

```plantuml
'インタフェースキーワードで interfaceを定義
interface インタフェース {
    + getValue()
}
```

- abstract class

```plantuml
'abstract class キーワードで抽象クラス を定義
abstract class 抽象クラス {
    {abstract} func()
}

```

- 継承

```plantuml
class クラスA {
    id: number
}

abstract class 抽象クラス {
    {abstract} func()
}


'<|-- で汎化(Generalization)
抽象クラス <|-- クラスA

```

- 実現

```plantuml
class クラスA {
    id: number
}


interface インタフェース {
    + getValue()
}

'<|.. で実現(Implementation)
インタフェース <|.. クラスB
```

- 関連

```plantuml
class A {

}

class B {

}

class C {

}

'-で関連
A-B
B-->C
A<--C
```

- 集約

```plantuml
class A {

}

class B {

}

'o-で集約
A o- B

```
- コンポジション

```plantuml
class A {

}

class B {

}

'*-でコンポジション
A *- B
```