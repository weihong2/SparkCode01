package com.bjsxt.sparkscalacode

import org.apache.spark.{SparkConf, SparkContext}

object ParalleLizeTest {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("test"))

    val rddd1 = sc.parallelize(Array[String]("a","b","c","a","e"),2)

    val rddd2 = rddd1.filter(x => {
      println("-------filter-----------" + x)
      true
    })

    val rddd3 = rddd2.map(x => {
      println("------map---------" + x)
    })
    rddd3.count()




//    val rdd1 = sc.parallelize(Array[Int](1,2,3,3,6,4,5,6,7),2)
//    println(rdd1.getNumPartitions)
//
//    val rdd2 = rdd1.map((_,1))
//    println(rdd2.getNumPartitions)
//
//    val rdd3 = rdd2.reduceByKey(_+_,4)
//    println(rdd3.getNumPartitions)
//
//    rdd3.count()
  }

}
