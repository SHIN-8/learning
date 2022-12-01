## Reduxによる状態管理
### Reduxとは
`Redux`は、Fluxアーキテクチャのライブラリ実装の1つです。他にも`mobX`や`Flux`などといったライブラリ実装があります。今回は、Amaryllisでも使用しているReduxについて取り上げます。

### Redux関連パッケージのインストール
`Redux`単体でも状態管理ライブラリとして利用可能ですが、`react-redux`パッケージと`redux-actions`パッケージをインストールしておくと、実装が少し楽になりますのでこれらも合わせてインストールします。

__npmの場合__ 
```sh
$ npm install redux react-redux redux-actions
```

__yarnの場合__ 
```sh
$ yarn add redux react-redux redux-actions
```

`react-redux`と`redux-actions`はそれぞれ、`React`コンポーネントと`Redux`の状態管理部分を接続するためのヘルパー、`redux-actions`は`ActionCreator`を実装するためのヘルパーです。

### ディレクトリの作成
`Redux-way`に則った`react+redux`のディレクトリ設計は、以下のように構造で紹介されていることが多いです。

```txt
├── src
│   ├── compnents
│   ├── containers
│   ├── actions
│   ├── reducers
│   └── stores

```

一方で、`Flux`アーキテクチャの要素のうち`action`・`reducer`・`store`は互いに依存しています。そのため、`action`を編集すれば`reducer`を編集しなければならず`reducer`の編集が必要であれば`store`にも変更が必要になります。

そのため、`action`・`reducer`・`store`を個別に管理するのではなく、1つのモジュールとしてみなして管理する手法があります。これを`Ducks`といいます。

Amaryllisにはこの`Duck`を採用しているため、今回のハンズオンでも`Ducks`を採用します。`Ducks`を使った場合のディレクトリ構造は以下のようになります。

```txt
├── src
│   ├── compnents
│   ├── containers
│   └── modules
```