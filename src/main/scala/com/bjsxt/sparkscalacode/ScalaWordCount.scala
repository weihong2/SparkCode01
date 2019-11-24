package com.bjsxt.sparkscalacode

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object ScalaWordCount {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("scalaWordCount"))
    /*sc.textFile("./data/words").
      flatMap(s => s.split(" ")).
      map(s => Tuple2(s,1)).
      reduceByKey(_+_).
      sortBy(_,false).
      foreach(println)*/

    new SparkContext(new SparkConf().setMaster("local").setAppName("scalaWC")).textFile("./data/words")
      .flatMap(_.split(" ")).map(Tuple2(_,1)).reduceByKey(_+_).sortBy(_._2,false).foreach(println)

    /*val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName("scalaWordCount");

    val sc = new SparkContext(conf)
    sc.textFile("./data/words").flatMap(_.split(" ")).map(Tuple2(_,1)).reduceByKey(_+_).sortBy(_._2,false).foreach(println)


    val lines: RDD[String] = sc.textFile("./data/words")
    val words: RDD[String] = lines.flatMap(line => {
      line.split(" ")
    })
    val pairWords: RDD[(String, Int)] = words.map(word => {
      new Tuple2(word, 1)
    })
    val reduce: RDD[(String, Int)] = pairWords.reduceByKey(func = (v1: Int, v2: Int) => {
      v1 + v2
    })
    val result: RDD[(String, Int)] = reduce.sortBy(tuple=>tuple._2,false)
    result.foreach(println)

    sc.stop()*/
    test
  }
  implicit val n = "hello"
  def test(implicit name:String): Unit ={
    println(s"$name")
  }
}