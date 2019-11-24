package com.bjsxt.scala_study

class test_class{
  println("class hello")
  def method1(xname:String): Unit = {
    //println(s"name = ${xname}")
    println(s"name = $xname")
  }
  method1("class_代码块")
  def method2(xname:String,xage:Int):Unit = {
    println(s"xname = $xname , xage = $xage")
  }
  method2("class_代码块",23)
}
class test_class2(yname:String,yage:Int,yhobby:Array[String]){
  val name = this.yname
  val age = this.yage
  val hooby = yhobby
  hooby.foreach(x=>println(x))
  println("*******************")
  //println(s"name = $name , age = $age , hooby = $hooby.foreach(x=>println(x))")
  //println(s"name = $name , age = $age , hooby = ${hooby}")
  println(s"name = $name , age = $age , hooby = ${hooby.foreach(y=>println(y))}")
  println("*******************")
}
class test_class3(zname:String,zage:Int){
  def this(z1name:String,z1age:Int,z2name:String){
    this(z1name,z1age)
    println(s"代码块test_class3: $z1name, $z1age, $z2name")
  }
  println(s"代码块test_class3: $zname, $zage")
  println(s"代码块test_class3: $scala_demo1.test_object")
  println(s"代码块test_class3: ${scala_demo1.test_object}")

}
object scala_demo1 {

  val test_object = 200;

  println("hello object")

  def main(args: Array[String]): Unit = {
    /*println("Hello, world")
    val a = 111
    val b = 222
    println("a = "+a+", b = "+b)
    println(s"a = $a , b = $b , a+b = ${a+b}")
    println(s"a = $a , b = $b , a+b = $a+b")*/
    val test_class = new test_class
    test_class.method1("test_name")
    test_class.method2("test_name",77777)

    val test_class2 = new test_class2("test_class2",33,Array("books","music","nba"))
    println("---------------------")
    println(s"class2_hooby = $test_class2.hobby")
    println(s"class2_hooby = $test_class2")
    test_class2.hooby.foreach(x=>println(x))
    //println(s"class2_hooby = ${test_class2.hobby}")
    println(s"class2_hooby = ${test_class2.name}")

    println("()()()()")
    new test_class3("zs",23,"lisi")
    println("()()()()")
    new test_class3("zs",23)
    println("()()()()")
  }
}
