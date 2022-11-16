プログラミング言語 Kotlin についての調査
====
# 概要
Kotlinは、JetBrains社が開発した、静的型付けのマルチパラダイムのプログラミング言語。
Java仮想マシン上で動作させることが可能なJVM言語の1つであり、JVMがインストールされている環境ではオペーレーティング・システムに関係なく動作させることがでできる。速度的には、Javaと遜色はないといわれている。version 1.3からネイティブでの実行もサポートされた。

# 開発環境
エディタやIDEとしては下記のものが使える。
- IntelliJ IDEA *
- Visual Studio Code 
- Atom
    

本稿では、Visual Studio Codeを使用することにした。

## セットアップ
公式ドキュメントを参照しKotlinのCLI環境のインストールを行った。
- [Installation ](https://kotlinlang.org/docs/tutorials/command-line.html)

筆者の環境はMacであるため、HomeBrewでインストールを行った。インストールコマンドは以下の通り。

```sh
$ brew update
$ brew install kotlin
```

## Visual Studio Code の拡張機能インストール
Visual Studio Code から拡張機能を検索すると、KotlinとKotlin Languageの拡張があったがKotlin Languageのほうがユーザー数が多くバージョンも高いので安定していると思われるのでこちらを選択した。
- [Kotlin](https://marketplace.visualstudio.com/items?itemName=fwcd.kotlin)
- [Kotlin Language](https://marketplace.visualstudio.com/items?itemName=mathiasfrohlich.Kotlin)

## Kotlin のコンパイルと実行
おなじみのHelloWorldを出力するプログラムを作成。

@import "./src/hello_world.kt"

このプログラムを実行可能なjar形式にするには、下記のコマンドを実行する。

```sh
kotlinc ./src/hello_world.kt -include-runtime -d build/hello_world.jar
```

コンパイルしたjarファイルを実行するには、下記のコマンドを実行する。

```sh
kotlin ./build/hello_world.jar
```

# 基本文法
## 変数定義
変数定義はvalキーワードを使って以下のように行う。Kotlinは型推論システムが優秀なので、変数の型を省略しても推論で補ってくれるが、右辺の式か型指定のどちらかが必須となる。

```kotlin
val {変数名}:{型} = 式
```

```kotlin
/**
 * define variable
 * val keyword 
 */
fun main(args: Array<String>) {
    val v:Integer
    //Number
    val value = 10
    println(value)

    //String
    val msg = "Programming Language Kotlin"
    println(msg)

    ///Array
    val array: Array<Int> = arrayOf<Int>(1, 2, 3)
    println(array)

}
```

## 関数定義
関数定義はfun キーワードを使う。

```kotlin
/**
 * define functions
 * fun keyword
 */
 fun sum(a:Int, b: Int): Int {
     return a + b
 }

fun main(args: Array<String>) {
    println("> Please input 1st integer.")
    val in1 = readLine()
    val a   = Integer.valueOf(in1)

    println("> Please input 2nd integer.")
    val in2 = readLine()
    val b   = Integer.valueOf(in2)

    println(sum(a, b))
 }
```
## クラス定義

```kotlin
/**
 * 空のクラス
*/
class Empty 


/**
 * Kotlinではコンストラクタ(プライマリコンストラクタ)はクラスヘッダの一部として宣言する
 * 継承元を明示しない場合は暗黙的にAnyを継承する。
*/
class Person(name: String, age: Int) {

    /**
     * constructorを使ってセカンダリコンストラクタを記述できる
     */
    constructor():this("匿名", 20)
    val name = name 
    val age = age

    /**初期化処理は initに書く */
    init {
        /**
         * requireを使って初期条件を記述できる。
         */
        require(name != "" && age >= 0)
    

        println("Person class initialized.")
    }

    
    /**
     * メソッド定義はfun キーワードにメソッド名
     */
    fun greet(): Unit {
        println(this.name + " " + this.age)
    }
}

fun main(args: Array<String>) {
    println("> Please input your name.")
    val name= readLine().toString()

    println("> Please input your age")
    val in2 = readLine()
    val age:Int   = Integer.valueOf(in2)

    val p1 = Person(name, age)
    p1.greet()

    val p2 = Person()
    p2.greet()
 }
```

## 制御構文
### 条件分岐
Javaと同様にifを使って条件分岐を記述できる。

```kotlin
fun main(args: Array<String>) {
    println("> Please a number.")
    val line = readLine()
    val n    = Integer.valueOf(line)

    /**
     * if 式は、Javaとほぼ同じ
     */
    if (n % 2 == 0 ) {
        println("$n number is even.")
    }
    else {
        println("$n is odd.")
    }

    /**
    Kotlinにおいてifは式なので値を返せる（＝３項演算子と同じに扱える）
     */
     val str = if(n%2 == 0) "even" else "odd"
     println(str)
 }
```

when構文を使うと複雑な条件分岐もかける。
```kotlin
enum class Enums{
    A,
    B,
    C,
    D
}

fun main(args: Array<String>) {
    println("> Please a number.")
    val line = readLine()
    val n    = Integer.valueOf(line)
    val bool = false
    val e    = Enums.A

    /**
     * when式で条件分岐
     * fallthrowは起こらないため、マッチしたところで処理が打ち切られる
     * 条件式は記載しなくても可能
     * マッチしない処理はelseでかく
     */
    when{
        n > 5 -> {
            println("$n is larger than 5")
        }
        bool -> {
            println("true")
        }
        e == Enums.A -> {
            println("$e is A")
        }
        else -> {
            println("else")
        }
    }

    /**
     * 条件式は記載しても良い
     * ==判定っぽい
     */
    when (n) {
        5 -> {
            println("$n equal 5")
        }
        else -> {
            println("else")
        }
    }

 }
```

### 繰り返し
for 文のループ
```kotlin
fun main(args: Array<String>) {
 //-------------
 //iterableオブジェクト
 //------------
    val list = listOf("banana", "apple", "tomato")
    for (item in list) { 
        print(item)
    }

 //-------------
 //回数指定で繰り返す
 //------------
    for (i in 1..4) { //..はRangeオブジェクトを作成する
        print(i) // 1234
    }

    for (i in 4 downTo 1) { //4...1はNG
        print(i) // 4321
    }

    for (i in 4 downTo 1 step 2) {
        print(i) // 4 2
    }

 }
```

while分を使ったループ

```kotlin

fun main(args: Array<String>) {
    println("> Please a number.")
    val line = readLine()
    var n    = Integer.valueOf(line)

    while (n > 0) {
        println(n--)
    }
 }

```

