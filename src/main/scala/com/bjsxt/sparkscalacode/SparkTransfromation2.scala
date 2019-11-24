package com.bjsxt.sparkscalacode

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object SparkTransfromation2 {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("test"))
    sc.setLogLevel("Error")

    val rdd4 = sc.parallelize(Array[String]("a","b","c","a","b","e"),3)

    rdd4.foreachPartition(iter=>{
      println("建立数据库连接。。。。。")
      val list = new ListBuffer[String]()
      while (iter.hasNext){
        list.append(iter.next())
      }
      println("插入数据：listBuffer")
      println("关闭连接。。。。。")
//      list.iterator
    })

    rdd4.mapPartitions(iter=>{
      println("建立数据库连接。。。。。")
      val list = new ListBuffer[String]()
      while (iter.hasNext){
        list.append(iter.next())
      }
      println("插入数据：listBuffer")
      println("关闭连接。。。。。")
      list.iterator
    })


    val rdd1 = sc.parallelize(Array[Int](1,2,2,2,3,4,5))
    val rdd2 = sc.parallelize(Array[Int](1,2,3,3,3,7,8))

    val intersection: RDD[Int] = rdd1.intersection(rdd2)
    val distinct: RDD[Int] = rdd1.distinct()//有shuffle，就能传分区数
    val subtract: RDD[Int] = rdd1.subtract(rdd2)

    val nameRDD = sc.parallelize(Array[(String, Int)](
      ("zs", 12), ("lisi", 33), ("sxt", 33)
    ))
    val scoreRDD = sc.parallelize(Array[(String, Int)](
      ("zs", 1), ("lisi", 22), ("sxt222", 333)
    ))


    /**
      * join,leftOuterJoin,rightOuterJoin,fullOuterJoin,union
      */
    nameRDD.join(scoreRDD).foreach(println)
    val unit: RDD[(String, (Int, Option[Int]))] = nameRDD.leftOuterJoin(scoreRDD)
    unit.foreach(t=>{
      val key = t._1
      val value1 = t._2._1
      val option = t._2._1
      println(s"key=$key , value=$value1 , option=$option")
    })
    val unit5: RDD[(String, (Option[Int], Option[Int]))] = nameRDD.fullOuterJoin(scoreRDD)
  }
}
