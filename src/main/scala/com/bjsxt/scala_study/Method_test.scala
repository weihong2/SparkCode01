package com.bjsxt.scala_study

import java.util.Date

import scala.collection.immutable

object Method_test {
  def main(args: Array[String]): Unit = {
    /**
      * 1.方法定义
      *   1).使用def
      *   2).参数类型要制定，返回值类型可以省略，自动推断
      *   3).建议不加return
      *       方法体中最后一行的计算结果自动当作返回值返回
      *      如果使用return，要显示的声明方法体的返回值类型
      *   4).方法体只有一行，可省略{}
      *   5).不加"="，返回值会被丢弃，Unit
      */
    def max(x:Int,y:Int)={
      if (x>y)
        x
      else y
    }
    //val i = max(100,300)

    Array("hello","world","to").foreach(println)
    def funaaa (a:Int=223,b:String="sd")={

    }
    /*println(")))))))))))")
    val aa = 10
    var bb =10
    def funaa(a:Int)=2+a
    println(funaa(aa))
    println(funaa(bb))*/

    /**
      * 2.递归方法：
      */
    def fun(a:Int):Int = {
      if (a==1){
        1
      }else{
        a * fun(a-1)
      }
    }
    val i = fun(5)
    println(i)

    /**
      * 3.参数有默认值的方法
      */
    def fun2(a:Int=12,b:Int=23)={
      a+b
    }
    //println(fun2(2,3))
//    println(fun2())
//    println(fun2(1))
//    println(fun2(a=1))
//    println(fun2(b=1))
//    println(fun2(a=1,b=2))

    /**
      * 4.可变长参数的方法
      */
    def fun3(s: String*)={
      println(s)

//      for (elem <- s) {
//        println(elem)
//      }

      //匿名函数
      s.foreach((x:String)=> {
       println(x)
      })
      s.foreach(x => println(x))
      //参数只用了一次，使用"_"
      s.foreach(println(_))
      //一个参数省略(_)
      s.foreach(println)
    }
    //fun3("a","b","c")

    /**
      * 5.匿名函数
      *     标志：“=>”
      */
    val fun4 = (a:Int,b:Int)=>{
      a + b
    }
    /*val fun41:(Int,Int) = (a:Int,b:Int)=>{
      a + b
    }*/
    // 函数类型：(Int,Int) => Int
    val fun42:(Int,Int) => Int = (a:Int,b:Int)=>{
      a + b
    }
    println(fun4(2,3))
    /**
      * 6.嵌套方法
      */
    def fun5(a:Int)={
      def fun51(b:Int):Int={
        if(b==1) 1 else b*fun51(b-1)
      }
      fun51(a)
    }
    println(fun5(5))



    /**
      * 7.偏应用表达式
      */
    def showLog(d:Date,log:String)={
      println(s"date is $d , log is $log")
    }
    val date = new Date()
    /*showLog(date,"aaa")
    showLog(date,"bbb")*/
    //val fun61 = showLog(date,_:String)
    //fun61("abc")


    /**
      * 8.高阶函数
      *   1).方法的参数是函数
      *   2).方法的返回时函数
      *   3).方法的参数和返回都是函数
      */
    // 参数是函数
    def fun7(a:Int,b:Int)={
      a + b
    }
    def fun71(s:String,x:Int,y:Int,f:(Int,Int)=>Int)={
      val result = f(x,y)
      s +"#"+ result
    }
    //println(fun71("sss",3,4,fun7))
    println(fun71("ooooo",2,2,(a:Int,b:Int)=>{
      a * b
    }))
    //2.返回值是函数
    def fun6()={
      (a:Int,b:Int)=>{
        a + b
      }
    }
    val fun61 = fun6()
    println(fun61(2,3))
    println(fun6()(2,4))
    //3.参数和返回值都是函数
    def fun8(a:Int,b:Int,f:(Int,Int)=>Int):(String,String)=>String={
      def fun81(s1:String,s2:String)={
        f(a,b) + "##" + s1 + "s2"
      }
      fun81
    }
    println("--=-=-=-=-=-=")
    println(fun8(2,4,(a:Int,b:Int)=>{
      a * b
    })(" aaaa "," bb "))
    println(fun8(2,4,_+_)(" aaaa "," bb "))
    println("--=-=-=-=-=-=")

    /*def fun9(a:Int,b:Int)(c:Int,d:Int)={
      a + b + c + d
    }
    println(fun9(1,2)(1,2))*/
    def fun9(a:Int*)(b:String*)={
      a.foreach(println)
      b.foreach(println)
    }
    fun9(22,34,55)("ab","dd","cc")
    println("--=-=-=-=-=-=")








    //    var jetSet = Set("hello","set")
//    jetSet.foreach(x => println(x))
//    jetSet += "lead"
//    jetSet.foreach(x => println(x))

//    val hex = 0x00f
//    println(hex)
//    println((-2.3).abs)

    //println(3 to 6)
    //println(1+2+s" ${1+2} "+s"${(1).+(2)}")
//    println((2.0).unary_-)
//    println("hello,world" indexOf('o'))
//    println("hello,world" indexOf'o')
//    println("hello,world" indexOf 'o')

    val rom = Map(1 -> "I",2 -> "II",3 -> "OOO")
    rom.foreach(x => println(x))
    rom.foreach(println)

//    val check = new Check
//    println("************")
//    println(check.sum)
//    println(check.add(12))
//    println(check.sum)
//    println(check.add2(23))
//    println(check.sum)
//    println(check.add3(23))
//    println(check.sum)
  }

  def apply():Unit={
    println("----------")
  }
  def apply(oname:String):Unit={
    println(s"----$oname------")
  }

  /*println("()()()()()()()()()()((((")
  println(1 to 5,2)*/
  for (i <- 1 to 5 ;if (i%2==0); j <- i to 5){
    print(s"$i , $j\t")
  }
  println()
  for (i <- 1 to 5 ;if (i%2)==0;if(i>2); j <- i to 5){
    print(s"$i , $j\t")
  }
  println()
  private val ints: immutable.IndexedSeq[Int] = for(i <- 1 to 6) yield i
  println(ints)
  println()
}
class Check{
   var sum = 0
  def add(b:Int)=sum +=b
  def add2(b:Int) {sum +=b}
  def add3(b:Int)=sum+b
}