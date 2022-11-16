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