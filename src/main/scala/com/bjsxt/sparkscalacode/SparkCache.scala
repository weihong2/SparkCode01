package com.bjsxt.sparkscalacode

import org.apache.spark.{SparkConf, SparkContext}

object SparkCache {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("test"))

    val lines = sc.textFile("./data/persistData.txt")

    lines.cache()

    val startTime1 = System.currentTimeMillis()
    val count1 = lines.count()
    val startTime2 = System.currentTimeMillis()
  }
}
