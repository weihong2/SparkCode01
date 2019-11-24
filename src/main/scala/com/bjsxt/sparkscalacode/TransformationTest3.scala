package com.bjsxt.sparkscalacode

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable.ListBuffer

object TransformationTest3 {
  def main(args: Array[String]): Unit = {
    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("test"))



    //val rdd1 = sc.makeRDD(List[(String,Int)](("zs",16),("list",33),("zs",16),("zs",99),("wangwu",25)))

    /*val rdd1 = sc.parallelize(Array[String](
      "love1","love2","love3","love4",
      "love5","love6","love7","love8",
      "love9","love10","love12","love13"
    ),3)*/

    /**
      *值是 K,V的RDD
      *    mapValues(func),只针对 K,V的RDD的value操作
      *值是map集合
      *    countByKey,只针对 K,V的RDD，相同的key合在一起,value为key出现的次数
      *    countByValue,相同的值合在一起,value为key出现的次数
      */
//    val rdd2: RDD[(String, Int)] = rdd1.mapValues(_+100)
//    rdd2.foreach(println)
//    val stringToLong: collection.Map[String, Long] = rdd1.countByKey()
//    stringToLong.foreach(println)
//    println("_____________________")
//    val rddd1 = sc.parallelize(Array[String]("a","b","c","a","e","b"))
//    val strr2: collection.Map[String, Long] = rddd1.countByValue()
//    strr2.foreach(println)
//    println("_____________________")
//    val tupleToLong: collection.Map[(String, Int), Long] = rdd1.countByValue()
//    tupleToLong.foreach(println)


    /**
      * reduce(func(s1,s2))合成字符串
      */
//    val rdd1 = sc.parallelize(Array[String]("a","b","c","a","e","b"))
//    val str: String = rdd1.reduce(_+" # "+_)
//    println(str)


    /**
      * zip(RDD) & zipWithIndex
      *   zip可用于计算多元一次方程
      *   zipWithIndex将值和下表合在一起
      */
//    val rdd1 = sc.parallelize(Array[String]("a","b","c","a","e","b"))
//    val rdd2 = sc.parallelize(Array[Int](1,2,3,4,5,6))
//    val result: RDD[(String, Int)] = rdd1.zip(rdd2)
//    result.foreach(println)
//    val unit: RDD[(String, Long)] = rdd1.zipWithIndex()
//    unit.foreach(println)


    /**
      * groupByKey，将相同key合在一起
      */
//    val rdd2: RDD[(String, Iterable[Int])] = rdd1.groupByKey()
//    rdd2.foreach(println)

    /**
      * coalesce(num,false)，可以设置是否产生shuffle，
      *   常用于合并小文件
      *   分区数：由少变多，即num大于父RDD，shuffle为false，不起作用
      */
//    val rdd2 = rdd1.mapPartitionsWithIndex((index, iter) => {
//      val list = new ListBuffer[String]()
//      while (iter.hasNext) {
//        list.append(s"rdd1 partition idex = {$index} , value = {${iter.next()}}")
//      }
//      list.iterator
//    })
//    val rdd3 = rdd2.coalesce(4,true)
//    val rdd4 = rdd3.mapPartitionsWithIndex((index, iter) => {
//            val list = new ListBuffer[String]()
//            while (iter.hasNext) {
//              list.append(s"rdd3 partition idex = {$index} , value = {${iter.next()}}")
//            }
//            list.iterator
//          })
//    val strings = rdd4.collect()
//    println(rdd3.getNumPartitions)
//    strings.foreach(println)

    /*
    * repartition(num) 重新分区，产生shuffle
    *   = coalesce(num,true)
    * */
//    val rdd2 = rdd1.mapPartitionsWithIndex((index, iter) => {
//            val list = new ListBuffer[String]()
//            while (iter.hasNext) {
//              list.append(s"rdd1 partition idex = {$index} , value = {${iter.next()}}")
//            }
//            list.iterator
//          })
//    val rdd3 = rdd2.repartition(2)
//    val rdd4 = rdd3.mapPartitionsWithIndex((index, iter) => {
//      val list = new ListBuffer[String]()
//      while (iter.hasNext) {
//        list.append(s"rdd3 partition idex = {$index} , value = {${iter.next()}}")
//      }
//      list.iterator
//    })
//    val strings = rdd4.collect()
//    strings.foreach(println)

    /*
    mapPartitionsWithIndex
    * */
    val rdd1 = sc.parallelize(Array[String](
      "love1","love2","love3","love4",
      "love1","love2","love3","love4",
      "love5","love6","love7","love8",
      "love9","love10","love12","love13"
    ),3)
    rdd1.foreachPartition(iter=>{
      println("---===---=-=-=-=")
      while (iter.hasNext){
        println(iter.next())
      }
    })
    val rdd2 = sc.makeRDD(List[(String,Int)](("zs",16),("list",33),("zs",16),("zs",99),("wangwu",25)))
    //val str: String = rdd1.reduce(_+" # "+_)
    //rdd2.reduce((a,b)=>a._1+" $ "+b._1)
    //rdd1.groupBy(_+" # ").foreach(println)
    //rdd1.groupBy()
    /*val value: RDD[(String, Int)] = rdd1.map((_,1)).mapValues(_+100)
    value.foreach(println)*/
//    rdd1.map((_,"$")).mapValues(_+"#").foreach(println)
//    println("________________________")
//    val rdd3: RDD[String] = rdd1.mapPartitions(iter => {
//      val list = new ListBuffer[String]()
//      while (iter.hasNext) {
//        list.append(s"mapPartitions--rdd1 partition value = {${iter.next()}}")
//      }
//      list.iterator
//    })
//    rdd3.foreach(println)
//    println("________________________")
//    val rdd2 = rdd1.mapPartitionsWithIndex((index, iter) => {
//      val list = new ListBuffer[String]()
//      while (iter.hasNext) {
//        list.append(s"WithIndex---rdd1 partition idex = {$index} , value = {${iter.next()}}")
//      }
//      list.iterator
//    })
//    //rdd1.mapPartitionsWithIndex()
//    val strings: Array[String] = rdd2.collect()
//    strings.foreach(println)
  }
}
