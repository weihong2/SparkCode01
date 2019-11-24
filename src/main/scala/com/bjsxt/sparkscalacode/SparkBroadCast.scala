package com.bjsxt.sparkscalacode

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.{SparkConf, SparkContext}

object SparkBroadCast {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("test"))

    val blackName = List[String]("zs","list")

    val bc: Broadcast[List[String]] = sc.broadcast(blackName)
    val bc2: Broadcast[List[String]] = sc.broadcast(List("zs","list"))

    val nameRDD = sc.parallelize(Array[String]("zs","lisi","wangwu"))

    nameRDD.filter(s=>{
      val value: List[String] = bc.value
      !value.contains(s)
    }).foreach(println)
  }
}
