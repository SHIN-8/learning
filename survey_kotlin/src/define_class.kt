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