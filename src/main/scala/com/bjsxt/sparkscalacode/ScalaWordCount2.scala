package com.bjsxt.sparkscalacode

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object ScalaWordCount2 {
  def main(args: Array[String]): Unit = {
    val context = new SparkContext(new SparkConf().setMaster("local").setAppName("scalaWC2"))
    context.textFile("./data/words").flatMap(_.split(" ")).map(Tuple2(_,1)).reduceByKey(_+_).sortBy(_._2,false).foreach(println)

    val lines: RDD[String] = context.textFile("./data/words")
    val words: RDD[String] = lines.flatMap(_.split(" "))
    val pairWords: RDD[(String, Int)] = words.map(s=>Tuple2(s,1))
    val reduce: RDD[(String, Int)] = pairWords.reduceByKey((v1, v2)=>{v1+v2})
    val result: RDD[(String, Int)] = reduce.sortBy(tp=>tp._2,false)
    result.foreach(println)

    context.stop()
  }
}
