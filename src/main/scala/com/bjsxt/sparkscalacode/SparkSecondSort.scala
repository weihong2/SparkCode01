package com.bjsxt.sparkscalacode

import org.apache.spark.{SparkConf, SparkContext}

object SparkSecondSort {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("test"))

    val lines = sc.textFile("./data/secondSort.txt")

    lines.map(line=>(MySort2(line.split(" ")(0).toInt,line.split(" ")(1).toInt),line)).sortByKey(false).foreach(s=>println(s._2))
  }
}
case class MySort2(x1:Int,x2:Int) extends Ordered[MySort2] {
  override def compare(that: MySort2): Int = {
    if (this.x1 == that.x1){
      this.x2 - that.x2
    }
    this.x1 - that.x1
  }
}