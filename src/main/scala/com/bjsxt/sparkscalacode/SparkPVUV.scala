package com.bjsxt.sparkscalacode

import org.apache.spark.{SparkConf, SparkContext}

object SparkPVUV {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("test"))

    val lines = sc.textFile("./data/pvuvdata")

    //pv
    lines.map(s=>(s.split("\t")(5),1)).reduceByKey(_+_).sortBy(_._2,false).foreach(println)
    //uv
    lines.map(s=>s.split("\t")(0)+"_"+s.split("\t")(5)).distinct().map(s=>(s.split("_"),1)).
      reduceByKey(_+_).sortBy(_._2,false).foreach(println)

    //pv
    lines.map(line=>{(line.split("\t")(5),1)}).reduceByKey(_+_).sortBy(_._2,false).foreach(println)
    //uv
    println("_____________________")
    lines.map(line=>{line.split("\t")(0)+"_"+line.split("\t")(5)}).distinct().map(one=>(one.split("_")(1),1))
      .reduceByKey(_+_).sortBy(_._2,false).foreach(println)
  }
}
