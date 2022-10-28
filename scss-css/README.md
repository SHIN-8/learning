---
marp: true
style: |
  section {background-color: #f7f3e9;font-family: "Helvetica Neue", "Helvetica", "Hiragino Sans", "Hiragino Kaku Gothic ProN", "Arial", "Yu Gothic", "Meiryo", sans-serif;color:#555;}
  blockquote {margin-bottom: 10px;}
  h1 {font-size: 45px;color:#5eaaa8;}
  h2 {font-size: 55px;color:#5eaaa8;}
  h4 {font-size: 35px;color:#555;}
  p {font-size: 30px;}
  strong {color:#f05945;}
  section.top {text-align:center;}
---
 <!-- _class: top -->
## CSS変数とSCSS変数について

#### 担当 : 大森

---

# CSSについてざっくりと。
- CSS (Cascading Style Sheets) は、ウェブページのスタイルを設定するコードです。
- プログラミング言語やマークアップ言語でもありません。スタイルシートの言語です。
- **CSSは、HTMLの要素を選択的にスタイルにするために使うもの**

---

# CSSの記述方法

###### 記述方法

```
セレクタ {
 プロパティ: 値;
}
```

###### 記述例

```
h1 {
 color: #0bd;
}
```

---

# CSS変数とは？
**（別名：カスタムプロパティ、CSS Variables）**
<br>
>CSS変数とは、CSSの値の再利用性と冗長性の削減を目的として、
CSSドキュメント内で定義される値のことです。

<br>
カオスになりがちなCSSの数値やカラーコードを変数に入れて<br>
メンテナンス性をあげようってイメージです。<br>
ちなみに後述するSCSSでは変数は以前から利用できました。

---

# CSSの変数　宣言方法
###### 記述方法
```
セレクター {
  --変数名: 値;
}
```
上記を:root { }内に記述します。**rootは<html>要素を表します。**

###### 記述例
```
:root {
 --box-color: #ff5454;
}
```
こちらはbox-colorという変数にカラーコード#ff5454をいれてます。
(備考：#ff5454はキングプリンターズのコーポレートカラーです。 )

---

# CSSの変数　呼び出し方

###### 記述方法

```
var(--変数名);
```

###### 記述例
```
background: var(--box-color);
```

###### 変数を呼び出せなかった場合のフォールバック
```
background: var(--box-color, pink);
```
var(--変数名, 代替値);と記述することでbox-colorの変数が読み込めなかった場合に、カラーコードpinkが呼び出されます。

---

# css_sampleファイルを例に見ていきましょう。

---

# 次にSCSSについてざっくりと。

SASSもSCSSも日本語ではサースとかサス、エスシーエスエスとか色々読み方あります。
今回の勉強会ではSCSSを**サス**と読んで進めていきます。

#### SCSS(SASS)概要
- CSSを拡張したメタ言語です
  - CSSに対して機能を拡張した言語。
- SCSSはSASSの違い
  - SCSS ＝ CSSと似た記述
  - SASS ＝ Rubyと似た記述

---

# SCSSのメリット

- ファイルを分割して管理、コンパイル時に一つに統合できる
- mixinという関数のようなものも使える。引数の設定も可能
- if文、for文などループ処理も使用可能
- 四則演算や変数を利用してメンテナンス性アップ
- 入れ子（ネスト）ができる

---

# scssをコンパイルしてみましょう

---

# scssコンパイラ
今回はNode.jsの`node-sass`パッケージを利用します。

```$ npm install -g node-sass```
上記をインストールでscssをcssに変換可能になってるはず。

本日配布した`0413/scss_sample`フォルダにターミナルで移動して
下記コマンドでscssをcssにコンパイルが行えます。
```$ node-sass ./output.scss ./output.css```

---

# CSSとSCSSの変数について
基本的にはcssの変数と同じですが記述方法が少し異なります。

---

# SCSSの変数　記述方法
###### 記述方法
```
$変数名: 値;
```

###### 記述例
```
$bg-1: #ddd;
```

###### 呼び出し方
```
.block {
  background-color: $bg-1;
}
```

---

# scss変数をコンパイルしてみよう。
sampleフォルダのinput.scssを開いてコンパイルをお願いします。

---


# CSS変数使わずにSCSSの変数だけでいいんじゃない？

SCSSのほうが何かと便利で一度SCSSで記述すると普通のCSSの書き方がとても手間に感じます。
ですが、SCSSの変数はコンパイル時にCSSに変換されてしまうので、ブラウザーの可変には対応できません。

- SCSS変数をコンパイルしたinput.css
- CSS変数を利用したstyle.css

をhtmlでinput.cssとstyle.cssを切り替えてみてください。

---

# まとめ
昔は使いにくくて暗記することがおおかったCSSも関数や変数が使えるようになり
エンジニアの方もとっつきやすくなったのではないかと思い今回のテーマにしました。

CSSとSCSSの変数など上手く使えば比較的簡単にサイトのダークモードの作成も可能です。
ぜひご活用ください。

---
