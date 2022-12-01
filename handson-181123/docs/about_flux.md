## Fluxアーキテクチャについて
### 従来のアーキテクチャ
Fluxアーキテクチャを見ていく前に、MVC/MVP/MVVMなどの従来のアプリケーションアーキテクチャについて確認します。

MVCのアーキテクチャは、プログラムの役割をビュー層・コントローラー層・モデル層に分けて、アプリケーションを設計します。これは現在でも広く使われている一般的な設計手法です。MVCのアプリケーションの構造は一般的に以下のようになります。

```plantuml

rectangle Model [
Model層
--
・アプリケーションデータ
・ビジネスルール
]
rectangle View[
View層
--
・任意のデータ対する情報表現
・プログラムの操作方法の提供
]
rectangle Controller [
Controller層
--
・ビューの操作を受け取り、
  モデル（データ）の更新を行う
・更新されたデータをビューに渡す
]

View<-d->Controller
Controller<-d->Model
```

MVCは今でも広く使われている設計手法ですが、問題もあります。MVCアーキテクチャでは、Controllerが、ViewとModelの橋渡しをするため、肥大化しやすいです（いわゆるFatController）。また、ModelとViewに対して双方向のデータフロー、ModelとViewが増えると指数関数的に複雑さが増します。

### Fluxアーキテクチャとは
#### Fluxとは
[Flux](https://facebook.github.io/flux/docs/overview.html#content)は、Facebookが提唱しているアーキテクチャですが、同社が提供しているReactのための状態管理ライブラリの名前でもあります。

Fluxはデータフローを __単一の方向に制限する__ ことで、データの変更を行う箇所を集約しアプリケーションの状態を制御しやすくします。

Flux自体は、Observerパターンなので理解することはさほど難しくないと思います。

#### Fluxアーキテクチャの構成要素
Fluxの構成要素は、`Action`・`Dispatcher`・`Store`・`View`の4つです。それぞれの意味を以下に示します。

- Action
    - Actionの名前とActionごとのデータを持つオブジェクト
- Dispatcher
    - ActionオブジェクトをReducerに割り当てる関数
- Store
    - アプリケーションデータの保持
    - データの更新
-  View
    - 画面にデータを表示する
    - Reactのコンポーネント

#### Fluxアーキテクチャのデータフロー
Fluxアーキテクチャのデータフローは次のようになります。Actionを起点に、一方向のデータフローを実現します。基本的には、Observerパターンです。
 
```plantuml
rectangle Action
rectangle Action1[
    Action
]
rectangle Dispatcher
rectangle Store
rectangle View

Action->Dispatcher: メッセージと\nデータを渡す
Dispatcher->Store: 受け取った\nメッセージと\nデータを元に更新
Store->View: 更新されたデータ\nをViewに反映
View-u->Action1: ビューの操作から\nアクションを作成する
Action1-d->Dispatcher
```
