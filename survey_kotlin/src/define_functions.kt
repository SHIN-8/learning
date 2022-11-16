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