package com.bjsxt.scala_study

object Trait_test {
  def main(args: Array[String]): Unit = {
    val human = new Human("zs",23)
//    human.read("ssss")
//    human.listen("rrrr")
//    println(human.a)
    val human2 = new Human("zs",333)
    println(human.isqul("sdf"))
    println(human.isqul(human2))
    println(human.isNotQul(human2))
  }
}
class Human(xname:String,xage:Int) extends Read with Listen with TTT {
  val name = xname
  val age = xage
  override def isqul(n: Any): Boolean = {
    //println(s"---$n")
    isInstanceOf[Human] && asInstanceOf[Human].name == this.name
  }

  override val a = 333
}
/**
  * trait
  *     不可传参数
  *     继承trait : trait1 with trait2
  *     方法可以有实现、也可无实现
  *     isranceo
  */
trait Read{
  //val a = 100
  def read(n:String): Unit ={
    println(s"$n is reading....")
  }
}
trait Listen{
  val a = 200
  def listen(n:String): Unit ={
    println(s"$n is Listening....")
  }
}
trait TTT{
  def isqul(n:Any):Boolean
  def isNotQul(n:Any):Boolean = !isqul(n)
}