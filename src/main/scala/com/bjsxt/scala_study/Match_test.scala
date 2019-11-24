package com.bjsxt.scala_study

object Match_test {
  def main(args: Array[String]): Unit = {
    val tuple = (10,true,10,10.0,10.3,"hello scala","xxxx")

    val iterator = tuple.productIterator

    iterator.foreach(matchTest)

    while (iterator.hasNext){
      matchTest(iterator.next())
    }
  }

  /**
    * 1.case _=> 相当于switch中default，没匹配上
    * 2.匹配值，匹配类型
    * 3.从上往下一次匹配，匹配一次自动终止
    * 4.match中有数据类型转换
    * @param value
    */
  def matchTest(value:Any)={
    value match {
      case 10 => {println("value is 10")}
      case i:Int => {println(s"type is Int, value=$i")}
      case b:Boolean => {println(s"type is Boolean, value=$b")}
      case d:Double => {println(s"type is Double ,value=$d")}
      case "hello scala" => {println("value is hello scala")}
      case _=> {println(s"no match ...")}
    }
  }
}
