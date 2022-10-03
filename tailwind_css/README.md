

# Tailwind cssについて

---



### はじめに

本日はTailwind cssについてです。

以下の[2021年に最もホットであったJavaScriptライブラリのランキング]()では6位に入っています。
2020年度は10位に入っていたので、注目度も上がってきているようです。

よく使われるCSSフレームワークの一つであるBootstrapは、フロントエンド開発にRreactやVue.jsが用いられることが多くなった昨今では、あまり使用されなくなってきているようです。

好意的な意見も否定的な意見もよく耳にするTailwind cssの概要とbootstrapや他のcssフレームワークなどと何が違うかなど簡単にまとめたので共有いたします。



>JavaScriptライブラリのトレンドを紹介している[bestofjs.org](https://bestofjs.org/)が、2021年に最もホットであったJavaScriptライブラリのランキングを発表しました。
>選考基準は累計スター数ではなく、『2021年の一年間で増えたスターの数』です。
>過去流行っていたけど落ち目となった技術は出てこないので、最近注目されている技術がわかります。



![01](/Users/kpu0562/Desktop/システム/study/0202_Tailwind_CSS/01.png)



---



### Tailwind CSSとは



Tailwind CSSの読み方は『テイルウィンド シーエスエス』です。
名前の由来ですがWeb制作者にとって「追い風（Tailwind）」となるようにとの意味が込められているようです。

>**TailwindCSSの概要**
>TailwindCSSとはWebサイト及びWebアプリケーションを作成するためのCSSフレームワークです。
>2019年にバージョン1.0がリリースされたばかりと数あるCSSフレームワーク比較的新しいものです。
>TailwindCSSはユーティリティーファーストを掲げており便利で汎用的なクラスがたくさん用意されています。
>ユーザーは用意された汎用的なクラスを自由に組み合わせて使用することができます。

[Bootstrap](https://getbootstrap.com/)のように、あらかじめ用意されたコンポーネントを利用してカスタムしていくのではなく、
必要なユーティリティクラスをhtmlタグに書いてデザインをカスタマイズしていきます。

また当社でも利用しているlaravelではversion8から
デフォルトcssフレームワークが`Bootstrap`から`Tailwind CSS`に変更されました。
その他、GitHub、Heroku、Twitch、Netflixといった企業でも採用されています。
このことからもTailwind CSSが注目され、利用するサービスが増えていることが伺えます。



---



### Tailwind cssの特徴



#### <b>ユーティリティファースト</b>

> ユーティリティ ＝ 役に立つこと。功利。

TailwindCSSでは既存のクラス(ユーティリティクラス)を
直接HTMLに適用することで要素のスタイルを設定できます。

従来のやり方だと、デザインをカスタムするにはカスタムされたCSSが必要です。
ユーティリティクラスを使用することでCSSを記述せずにカスタムデザインを構築できることが大きな特徴の一つです。

また従来のCSSの書き方である

- まずは名前をつけてからスタイルを当てる
- まずはHTMLとCSSで擬似的にコンポーネントを作る

ではなく、ユーティリティーファーストだと、これを逆にできます。

- まずはスタイルを当てる
- 2度以上使われたらコンポーネントとして考える



#### <b>CSSファイルサイズを小さくする</b>

TailwindCSSはCSSファイルサイズを小さくするという特徴があります。
Tailwindでは「Purge CSS」というツールを使用することで使われていないCSSの記述をCSSファイルから削除することや最終的なビルドサイズを最適化することができます。



#### <b>スタイルの変更をHTMLローカルで安全に行える</b>

TailwindCSSはスタイルの変更をHTMLローカルで安全に行えるという特徴があります。
HTMLのclassはローカルであるため、他の何かが壊れることを心配せずにclassを変更することできます。



---



### ユーティリティクラス

CSSでのユーティリティクラスとは、
`.font-small {font-size: 10px;}` や `.text-left {text-align: left;}` のように、
単一のプロパティを定義した汎用的なクラスを指します。

ユーティリティクラスを用いた場合とコンポーネントを用いた場合で、どのような違いが有るか、
コンポーネント側の例としてBootstrapと比較して説明します。



Bootstrapでボタンを表示する際は以下のようなコードになります。

```html
<!-- html -->
<button class="btn btn-primary" type="button">Click Me!</button>
```



一方、Tailwind CSSを用いた場合だと、以下のようなコードになります。

```html
<!-- html -->
<button class="bg-blue-500 text-white font-bold py-2 px-4 rounded">Click Me!</button>
```
このように、Bootstrapでは、 `btn` というコンポーネントが定義されていて、それを適用しますが、
Tailwind CSSでは、classを自分で組み合わせてボタンを作る必要があります。




その為、コード量はBootstrapの方が少なくなりTailwind CSSでは多くのクラスを書く必要があります。

ただし、Tailwind CSSには@applyという書き方で、Tailwind CSSのクラスをまとめた独自のclassを定義することができるので、短い記述で済ませることもできます。

@applyについては後ほど説明いたします。



---



### Bootstrapとの違い



TailwindCSSとBootstrapもフレームワークが用意しているユーティリティクラスを利用するという点では同じです。

しかし、もっとも大きな違いは自由度の高さです。

例えば指定できる色については

- Bootstrap: 13個ほど
- TailwindCSS: 90個ほど

これくらいの差があります。



例えば、Bootstrapで文字を赤色を使いたい場合は`text-danger`の1つしかないんですが、

TailwindCSSだと`text-red-400`だったり`text-red-600`と、色を100, 200, …と100ごとに指定できるんですね。


ただ、Bootstrapがダメかというとそういうわけでもなくて、BootstrapとTailwind CSSそれぞれに長所・短所があります。

Tailwind CSSの長所は

```
- 自由度が高い
- Bootstrapより直感的に使える
```

短所は
```
- 記述がBootstrapより長い
- 参考記事が少ない
```

Bootstrapはこれを逆にしたのが長所と短所です。


まとめると

**「自由度が高いけど、その分複雑になる = Tailwind」**

　　　　　　　　　　or

**「多少ありきたりなデザインだけど、簡潔になる = Bootstrap」**



---



### インラインスタイルとの違い



htmlタグ内でのスタイリングであれば、インラインスタイルという方法と同じなのでは？と思う方もいるかもしれませんが、ユーティリティクラスはインラインスタイルと比べ、はるかに利便性が高いです。
具体的には以下の3点が利点として考えられます。



<b>1.デザインに一貫性を保てる</b>
インラインスタイルの場合、開発者が自由にスタイリングすることが可能な為、ページによってデザインが異なる、といった事象が発生してしまう可能性があります。
   Tailwind CSSは、あらかじめ定義されたユーティリティクラスのみ使用できるので、デザインに統一性を持たせることができます。

<b>2.ブレークポイントの指定が可能</b>
   インラインスタイルでは、ブレークポイントの指定はできませんが、Tailwind CSSであれば、htmlタグ内でレスポンシブに対応した指定ができます。
   一般的な[ブレークポイン](https://tailwindcss.com/docs/breakpoints)トがあらかじめ定義されている(カスタムすることも可能です)ので、ブレークポイントのキーをユーティリティクラスの前につけてあげればhtml内でレスポンシブ対応を完結させることができます。

<b>3.擬似クラスの指定が可能</b>
インラインスタイルでは、HoverやFocus、Activeなど擬似クラスの指定ができませんが、Tailwind CSSでは、htmlタグ内で擬似クラスの指定ができます。




---



### apply 設定方法

以下はtailwind cssで、@applyを使用しクラス記述を短くする例となります。

```css
/* tailwind.css */
@tailwind base;
@tailwind components;

.btn-primary {
  @apply bg-blue-500 text-white font-bold py-2 px-4 rounded
}

@tailwind utilities;
```


@applyを使用して実装したボタン

```html
<!-- html -->
<button class="btn-primary">Click Me!</button>
```



@applyを使用せずにユーティリティークラスのみで実装したボタン

```html
<!-- html -->
<button class="bg-blue-500 text-white font-bold py-2 px-4 rounded">Click Me!</button>
```



@applyを利用するタイミングですがコンポーネント内やhtml内で2回以上同じクラスの組み合わせが存在する場合に@applyを考えます。

ただ、コンポーネント内で@applyを使用しなければならない場合は、そもそもコンポーネントの定義を間違えている場合があるかもしれません。



---



### メリット・デメリット



####  <b>メリット </b>

- デザインシステムとは相性が良い。
  余白やカラーなど値がある程度決まっているため、デザインの統一が図りやすい。
  
- HTMLからスタイルの修正が容易
  該当するhtmlのユーティリティクラスを修正するだけでいいので、恐れることなくスタイルの修正を行えます。
  
- 命名規則を考える必要がない
  ただしコンポーネントにまとめる場合には名前をつける必要はある。
  
- PurgeCSSで、使ってないCSSを削除してファイルサイズを小さくできる。

- JS依存なし

  Tailwind CSS はスタイルがないライブラリなので JavaScirpt の依存がありません。
  Bootstrap はアコーディオンはツールチップなどのコンポーネントもあるためJSを読み込む必要があります。



####  <b>デメリット </b>

- クラスが長くなる。
  @applyを利用してHTML側を短縮はできますがcssでユーティリティークラスを記入することになるので混乱しやすい。

  ```css
  /*CSS*/
  .c-input {
    @apply block appearance-none bg-white placeholder-gray-600 border border-indigo-200 rounded w-full py-3 px-4 text-gray-700 leading-5 focus:outline-none focus:border-indigo-400 focus:placeholder-gray-400 focus:ring-2 focus:ring-indigo-200;
  }
  ```

  

- クラス名を考える必要がなくなる代わりにコンポーネント名や@applyの命名は必要になります。

- bootstrapとの併用はできない。

- クラス名をある程度覚える必要がある為学習コストが高い。

- このリンク先でtailwindいらない熱がだいぶ加速したようです。
  https://coliss.com/articles/build-websites/operation/css/why-tailwind-css-is-not-for-me.html



---



### Tailwind.cssに向きサイト・向いていないサイト



#### 向いているサイト

React や Vue といったフレームワークを用いてコンポーネントベースでフロントエンドを開発するにはかなり有用。

```sh
.
├── App.vue
├── components
│   └── Button.vue
├── main.ts
├── views
│   └── Page.vue
```

```html
<!-- components/Button.vue -->

<template>
  <button class="my-2 px-4 py-2
                 border-2 border-blue-500 rounded-md
                 bg-gradient-to-b from-blue-600 to-blue-400
                 hover:from-blue-500 hover:to-blue-300 
                 text-white shadow-lg" @click="clicked">
    ボタン
  </button>
</template>

<script lang="ts">
import { defineComponent } from "vue";
export default defineComponent({
  props: {
    clicked: {
      type: Function,
      default: {}
    }
  }
</script> 
```

```html
<!-- views/Page.vue -->

<template>
  <div class="flex">
    <div class="relative w-full">
      <Button :clicked="clicked" />
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import Button from "@/components/Button.vue"

export default defineComponent({
  components: {
    Button,
  },
  setup() {
    const clicked = () => alert("click!")

    return {
      clicked
    }
  },
})
</script>
```



#### 向いていないサイト

- HTML / CSSで構成されているサイト

- 一部でbootstrapが利用されているサイト
  - 併用することは可能だが地獄を見たと記載はありました。

- 管理画面などデザインの比重が高くないサイト
  ボタンやテキストフォーム、アラートのようなよくあるものを組み合わせて構築するよう場合はbootstrapの方が向いている。



---



### さいごに


資料ブログに記載のあった文章で勉強会を終了したいと思います

> 「私はHTMLとCSS書いてるけど使う機会が無さそうだな」とか思ってしまう方が多いようですが、自分から何もアクションをせず、Tailwind CSSを使うような機会は、この業界でフツーに働いていてもほとんどの人に訪れないんじゃないかなーと思います。
> Tailwind CSSがものすごいメジャーなものとならない限り。
>
> しかし、この技術を使えば○○が効率的にできる、○○の問題を解決できると考え、
> 自分から動いてアクションした結果、そういう仕事が依頼されたり、高い給与で雇われたり、高品質なWebサイトやWebサービスが完成するのではないでしょうか？ 
> そういうマインドセットを大事にしたいです。



ご静聴ありがとうございました。
