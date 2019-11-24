package com.bjsxt.sparkscalacode

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.control.Breaks

object SparkGroup1 {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("test"))
    /*val test: SparkSession.Builder = SparkSession.builder().master("local").appName("test")
    val session: SparkSession = SparkSession.builder().master("local").appName("test").getOrCreate()
    val context: SparkContext = session.sparkContext*/
    //val sc = SparkSession.builder().master("local").appName("test").getOrCreate().sparkContext

    val lines = sc.textFile("./data/scores.txt")

    lines.map(s=>(s.split("\t")(0),s.split("\t")(1).toInt)).groupByKey().foreach(s=>{
      val key = s._1
      val iter = s._2.iterator
      val ints = new Array[Int](3)

      val loop = new Breaks
      while (iter.hasNext){
        val next = iter.next()
        loop.breakable(
          for (i <- 0 until ints.length){
            if (next>ints(i)){
              for (j <- (ints.length-1).until(i,-1)){
                ints(j) = ints(j-1)
              }
              ints(i) = next
              loop.break()
            }
          }
        )
      }
      println(s"class is $key")
      ints.foreach(println)


      /*val loop = new Breaks
      while (iter.hasNext){
        val next = iter.next()
        loop.breakable()
        for (i <- 0 until ints.length){
          if (next>ints(i)){
            for (i <- ints.length-1 until i){
              ints(i) = ints(i-1)
            }
            ints(i) = next
          }
        }
      }*/


      /*//原生集合排序
      val iterable: Iterable[Int] = s._2
      val list = iterable.toList
      val listSort = list.sortWith(_>_).take(3)
      println(s"class is $key")
      listSort.foreach(println)*/
    })
  }
}
