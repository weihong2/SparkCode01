package com.bjsxt.scala_study

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

object String_test {
  def main(args: Array[String]): Unit = {
    /**
      * 数组：
      *   Array[](值)，new Array[](长度)
      */
    var arr2 = new Array[String](2)
    arr2(0) = "aa"
    val arr = Array[String]("a","b","v","e")
    //for (elem <- arr) {println(elem)}
    //arr.foreach((x:String)=>{println(x)})
    //arr.foreach(println)

    //二维数组
    val arr3 = new Array[Array[String]](3)
    arr3(0) = Array[String]("aaa","bbb","vvv")
    arr3(1) = Array[String]("aaa2","bbb","vvv")
    arr3(2) = Array[String]("aaa3","bbb","vvv")

    //arr3.foreach()
    /*for (arre <- arr3; elem <- arre)
      println(elem)*/

    //方法
    val arr4 = Array[String]("a","b","v","a","b","v")
    val strings = arr4.filter(one => {
      !"a".equals(one)
    })
    val i = arr4.count(one => {
      "a".equals(one)
    })
    println(i)

    //可变长度的数组
    val arrbuf = new ArrayBuffer[String](3)
    arrbuf.append("aaa","bbb")
    arrbuf.+=("end")
    arrbuf.+=:("start")
    //arrbuf.foreach(println)

    /**
      * List:
      */
    val list = List[Int](1,2,3,4,5)

    //方法：filter
    list.filter(one=>{
      one>=3
    }).foreach(println)

    //方法：map
    val listMap = List[String]("aa bb","ccd ed","tt uu")
    val stringses: List[Array[String]] = listMap.map(one => {
      one.split(" ")
    })
    stringses.foreach(one=>{
      println("*********&&*")
      one.foreach(println)
    })

    //方法：flatMap
    listMap.flatMap(one=>{
      one.split(" ")
    }).foreach(println)
    listMap.flatMap(one=>{
      one.split(" ")
    })
    /*listMap.flatMap(one=>{
      one.split(" ")
    }).foreach(o=>{
      println("--------------")
      o.foreach(println)
    })
*/
    val listBuffer1 = ListBuffer[String]("aa","bb")
    listBuffer1.append("cc","dd")
    listBuffer1.+=("ff","eee")
    //listBuffer1.foreach(println)
    //new ListBuffer[String]()
    //new ArrayBuffer[String](7)

    /*set*/
    val set = Set[String]("cccccc","bbbbbbbbbbbb","zz","aa","aaaaaaaaaaaaa")
    val set2 = Set[String]("aaaaaaaaaaaaa","xxxxxxxxxxx","cccccc")
    set.foreach(println)
    println("-------------")
    set.diff(set2).foreach(println)
    println("-------------")
    set.intersect(set2).foreach(println)
    //set.subsetOf(set2)
    val toList = set.toList
    println("[}{}{}{}{}}")
    //toList.foreach(println)
    println(set.max)
    println(set.min)
    println("[}{}{}{}{}}")
    //set.mkString()
    println("00000000000")
    //println(set.mkString)
    println("00000000000")
    //println(set.mkString("\t"))
    val set3 = scala.collection.mutable.Set[String]("ooo","xxx")
    set3.add("uuu")
    set3.+=("end")
    set3.+=("start","start","start2")
    //set3.foreach(println)
    println("setsetsetsetsetsetsetsetsetsetsetset")
    //set.foreach(println)

    /*map*/
    val map = Map[Int,String](1 -> "zs",(4,"qingshan"),2 -> "list",2 -> "wangwu",3 -> "xiaoliu")
    //map.foreach(println)
    for (elem <- map) {
      //println(elem)
      //println(s"key = ${elem._1} , value = ${elem._2}")

    }
    val maybeString: Option[String] = map.get(4)
    //println(maybeString)
    //println(maybeString.getOrElse("oxxx"))
    //println(maybeString.get)

    val keys: Iterable[Int] = map.keys
    for (elem <- keys) {
      //println(elem)
      //println(s"key = $elem , value = ${map.get(elem).get}")
    }

    val values: Iterable[String] = map.values
    //values.foreach(println)

    //map.foreach(println)

    val map2 = Map[String,Int]("zs"->10,("lisi",20),"wangwu"->33)
    val map3 = Map[String,Int]("zs"->100,("lisi",200),"xiaoliu"->330)
    val mapaa: Map[String, Int] = map2. ++ (map3)
    val stringToInt: Map[String, Int] = map2.++:(map3)

    val mapmut = scala.collection.mutable.Map[String,Int]()
    mapmut.put("zhangsan",22)
    mapmut.put("zhangsan",33)
    mapmut.put("lisi",33)
    //mapmut.foreach(println)
    //mapmut.get("zhangsan").get
    val intToString = scala.collection.mutable.Map(1 -> "zhangsan" , 2 -> "lisi")
    //println(intToString.get(2))
    intToString.get(2).getOrElse()
    println("+++++++++++")
    for(elem <- intToString){
      //println(elem)
      println(s"key = ${elem._1} , value = ${elem._2}")
    }
    println("+++++++++++")
    for(elem <- intToString.keys){
      println(s"key = $elem , vlaue = ${intToString.get(elem).get}")
    }
    //Tuple2("zhangsna","lisi")

    /*Tuple*/
    val tuple: (Float, Char, String, Int) = Tuple4(1.2f,'c',"hello",122)

    val tuple2 = (1.2f,'c',"hello",122)

    val iterator: Iterator[Any] = tuple2.productIterator
    while (iterator.hasNext){
      //println(iterator.next())
    }
    //println(tuple2.toString())

    println("##############")
    val string2: List[String] = List[String]("hello java","hello scala","hello c++")








    /*val s = "bjsxt"
    val ss = "BJSXT"

    //println(s.indexOf(98))
    //println(s.equalsIgnoreCase(ss))

    val builder = new StringBuilder()
    builder.append("bb").append("aaa")
    println(builder)*/

  }
}
