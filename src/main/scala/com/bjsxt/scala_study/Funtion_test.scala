package com.bjsxt.scala_study

object Funtion_test {
  def main(args: Array[String]): Unit = {
    def fun1(a:String,b:String)={
      a + b
    }
    //println(fun1("aa","bb"))
    def fun2(a:Int=120,b:Int=12)={
      a + b
    }
    //println(fun2(b=3))
    def fun3(a:Int,s:String*)={
      print(a)
      s.foreach(println)
    }
    //fun3(23,"asd","ads23")
    //匿名函数
    val funVal = (s:String,a:Int)=>{
      s.foreach(println)
      println(a)
    }
    //funVal("s",9)
    val funVal2:(Int,Int)=>Int = (a:Int,b:Int)=>{
        a + b
    }
    //println(funVal2(2,3))

    //偏应用表达式
    def fun4(s:String,b:Int)={
      println(s"s = $s , b = $b")
    }
    //fun4("sss",23)
    val fun41 = fun4("ssss",_:Int)
    //fun41(3)

    //高阶函数
    //1.参数是函数
    def fun5(s:String,a:Int,b:Int,f:(String,Int,Int)=>String)={
      println(f(s,a,b))
    }
    fun5("fun5",2,3,(s:String,a:Int,b:Int)=>{
      s + a + b
    })
    //2.返回值是函数
    def fun6()={
      (a:Int,b:Int)=>{
        a + b
      }
    }
    val fun61 = fun6()
    println(fun61(2,3))
    println(fun6()(2,4))


    def fun8():(String,String)=>String={
      def fun81(s1:String,s2:String)={
        "##" + s1 + "s2"
      }
      fun81
    }
  }
}
