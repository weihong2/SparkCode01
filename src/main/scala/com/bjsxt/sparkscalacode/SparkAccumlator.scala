package com.bjsxt.sparkscalacode

import org.apache.spark.util.LongAccumulator
import org.apache.spark.{SparkConf, SparkContext}

object SparkAccumlator {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("test"))

    val accumulator: LongAccumulator = sc.longAccumulator
    val lines = sc.textFile("./data/words",2)

    var i = 0
    lines.map(line=>{
      i+=1
      //println(i)
      accumulator.add(1)
      line
    }).collect()
    println(s"Driver i = $i")
    println(s"Driver accumulator = ${accumulator.value}")
  }
}
