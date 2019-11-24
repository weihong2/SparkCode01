package com.bjsxt.sparkscalacode

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SparkCheckpoint {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("che"))

    sc.setCheckpointDir("./ck")

    val lines: RDD[String] = sc.textFile("./data/words")

    lines.checkpoint()

    lines.count()

  }
}
