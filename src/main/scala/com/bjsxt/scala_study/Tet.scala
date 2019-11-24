package com.bjsxt.scala_study

/**
  * for:
  *  1.for(i <- 1 to 10)
  *  2.双层：for(i <- 1 to 10:j <- 1 to 10)
  *  3.加条件：for(i <- 1 to 10 if(i>5) if(i<10))
  *  4.yeild关键字：val a = for(i <- 1 to 10) yeild i
  */
/**
  * Scala中没有i++、++i
  *     i+=1;
  * while ... && do ... while()
  * if... else if ...else
  */
object Tet {
  def main(args: Array[String]): Unit = {
    var b = for (i <- 1 to 10) yield i
    println(b)

//    for(i <- 1 to 10 if(i%2==0) if(i>=5))
//      println(i)

//    for(i <-1 to 9;j <-i to 9){
//      print(s"$i * $j = ${i*j}\t")
//      if(i ==j)
//        println()
//    }


//    for(i <-1 to 5;j <-1 to 4){
//      println(s"i = $i, j = $j")
//    }
//
//    println(1 to (5,2))
//    println(1 until 3)
//
//    for(i <- 1 to 10)
//    println(i)
//
//    println("###########")
//    val a10 = (24,"tuple")
//    println(a10._1)
//    println(a10._2)
//
//
//    println("&&&&&&&&&&&&&&&&&&")
//    val a5 = List("hello",44)
//    val a6 = List("hello",44)
//    val a7 = a5 ::: a6
//    for (elem <- a7) {
//      println(elem)
//    }
//    println("%%%%%%%%%%")
//    val a8 = 2 :: 4 :: Nil
//    val a9 = 2 :: "kkkkk" :: Nil
//    a9.foreach(x => println(x))
//    println("%%%%%%%%%%")
//
//    val a = Array("hello","world")
//    val a2 = Array(23,"array")
//    val a3 = List(35,"list")
//    a.foreach(x=>println(x))
//    a2.foreach(x=>println(x))
//    a3.foreach(x=>println(x))
//    println("-----------")
//    for (elem <- a) {
//      println(elem)
//    }
//    val a4 = new Array[String](3)
//    a4(0) = "test_a4_hello"
//    a4.foreach(y=>println(y))
//
  }
}
