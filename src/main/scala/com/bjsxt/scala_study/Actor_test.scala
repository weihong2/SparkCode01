package com.bjsxt.scala_study

import scala.actors.Actor;

class AcotrDemo extends Actor{
  override def act(): Unit = {
    while (true){
      receive{
        case name:String => println("hello "+name)
        case money:Int => println("how much "+money)
      }
    }
  }
}

case class Register(username:String,password:String)
case class Login(username:String,password:String)
class AcotrDemo2 extends scala.actors.Actor{
  override def act(): Unit = {
    while (true){
      receive{
        case Register(username,password) => println("Register "+username+" pwd "+password)
        case Login(username,password) => println("Login "+username+" pwd "+password)
      }
    }
  }
}
object Actor_test {
  def main(args: Array[String]): Unit = {
//    val acotrDemo2 = new AcotrDemo2
//    acotrDemo2.start()
//    acotrDemo2 ! Register("zs","123123")
//    acotrDemo2 ! Login("lisi","123123")

//    val acotrDemo = new AcotrDemo
//    acotrDemo.start()
//    acotrDemo ! "tom"
//    acotrDemo ! 1000

    val myActor = new MyActor1
    val myActor2 = new MyActor2(myActor)
    myActor.start()
    myActor2.start()
  }
}
case class MSG(actor: Actor,message:String)
class MyActor1 extends Actor{
  override def act(): Unit = {
    while (true){
      receive{
        case msg:MSG => {
          if ("hello".equals(msg.message)){
            println(s"${msg.actor} : ${msg.message}")
            msg.actor ! MSG(this,"hi...")
          }else if("see you tomorrom".equals(msg.message)){
            println(s"${msg.actor} : ${msg.message}")
            msg.actor ! MSG(this,"no no no")
          }
        }
        case s:String => {println(s"match $s")}
        case _ => println("MyActor1 no match ....")
      }
    }
  }
}
class MyActor2(a: Actor) extends Actor{
  a ! MSG(this,"hello")
  override def act(): Unit = {
    while (true){
      receive{
        case msg:MSG => {
          if ("hi...".equals(msg.message)){
            println(s"${msg.actor} : ${msg.message}")
            msg.actor ! MSG(this,"see you tomorrom")
          }else if("no no no".equals(msg.message)){
            println(s"${msg.actor} : ${msg.message}")
          }
        }
        case _ => println("MyActor2 no match ....")
      }
    }
  }
}