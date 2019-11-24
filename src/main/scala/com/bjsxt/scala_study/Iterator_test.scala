package com.bjsxt.scala_study

object Iterator_test {
  def main(args: Array[String]): Unit = {
    val list = List[String]("hello java","hello scala","hello C")

    val iter: Iterator[String] = list.iterator
    val iterator2: Iterator[Any] = list.productIterator
    //iter.flatMap()
    //iterator2.flatMap()
    val flatMapList2: Iterator[String] = iter.flatMap(x => {
      println(s"-----flatMap--$x---")
      x.split(" ")
    })
    val mapList2: Iterator[String] = flatMapList2.map(x => {
      println(s"-------map----$x----")
      x + "#"
    })
    while (mapList2.hasNext){
      println(mapList2.next())
    }
    println("-=-=-=-=-=-=-=-=-=-=-==--==-=====---------------=")



    val flatMapList: List[String] = list.flatMap(x=>{
      println(s"-----flatMap--$x---")
      x.split(" ")
    })
    val mapList: List[String] = flatMapList.map(x=>{
      println(s"-------map----$x----")
      x+"#"
    })
    mapList.foreach(println)


    //val tuple: (String, String, String) = ("hello java","hello scala","hello C")
    //val iterator: Iterator[Any] = tuple.productIterator
    //iterator.foreach(println)
    //iterator.flatMap()
  }
}
