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