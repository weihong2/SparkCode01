package com.bjsxt.scala_study

object PartialFun_test {
  //偏函数
  //只能传一个类型，出一个类型
  def fun:PartialFunction[String,Int]={
    case "hello" => 10
    case "a" => 20
    case _ => 1111111111
  }

  def main(args: Array[String]): Unit = {
    println(fun("hello"))
    println(fun("a"))
    println(fun("b"))

    val clazz = new CaseClass("zs",33)
    val clazz2 = CaseClass("zs",33)
    println(clazz2.xname+"\t"+clazz2.xage)

  }
}

/**
  * 样例类：类定义时用"case"
  * 1.参数就是样例类的属性，public
  * 2.自动实现了toString,equals,copy和hashCode等方法
  * 3.样例类可new，也可以不new
  */
case class CaseClass(xname:String,xage:Int){

}