package com.bjsxt.scala_study

/**
  * 隐式值、隐式参数
  *   1.方法前参数由implicit修饰，参数为隐式参数，
  *       调用此方法时，可以不传入参数，寻找同一个作用域下相同类型的隐式值
  *   2.同一个作用域下相同类型的隐式值只能有一个
  *   3.部分隐式参数，使用柯里化函数，隐式参数写到后面括号中
  */
object Implica_test {

  //隐式值：在同一个作用域中同类型只有一个
  implicit val n = "zhangsan"
  implicit val a = 10

  //隐式参数，寻找同一个作用域中类型相同的隐式值
  def fun(implicit name:String,age:Int)={
    println(s"name is $name , age is $age")
  }
  //部分隐式
  def fun2(boolean: Boolean)(implicit name:String,age:Int)={
    println(s"name is $name , age is $age")
  }

  def main(args: Array[String]): Unit = {
    //fun
    //fun("wangwu",23)
    //fun2(true)


    val pig = new Pig("xiaozhu")
    //pig.fly()
    //pig.swim()
    new Car("xiao car").run()
  }

  /**
    * 隐式转换函数
    *   1.同一个作用域下相同类型(参数和返回值类型为一组)只能有一个，与函数名无关
    *   2.相对继承来说，具有继承的代码扩展性，却具有很强的解耦性
    * @param pig
    */
  implicit def pigToBird(pig: Pig):Bird={
    new Bird(pig.name)
  }
  implicit def pigToFish(pig: Pig):Fish={
    new Fish(pig.name)
  }

  /**
    * 隐式转换类
    * @param car
    */
  implicit class AAA(car:Car){
    def run()={
      println(s"${car.name} can run....")
    }
  }
}
class Bird(xname:String){
  val name = xname
  def fly()={
    println(s"$name can fly....")
  }
}
class Fish(xname:String){
  val name = xname
  def swim()={
    println(s"$name can swim....")
  }
}
class Pig(xname:String){
  val name = xname
}
class Car(xname:String){
  val name = xname
}
