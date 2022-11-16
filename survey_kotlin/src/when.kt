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
     * when構文を使って条件分岐
     * fallthroughは起こらないため、マッチしたところで処理が打ち切られる
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