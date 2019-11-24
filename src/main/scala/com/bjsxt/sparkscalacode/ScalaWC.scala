package com.bjsxt.sparkscalacode

import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

object ScalaWC {
  def main(args: Array[String]): Unit = {
    //Yarn中节点8个核，8G内存
    /**
      * conf 可以配置Spark，配置信息：
      *  1.配置Spark运行模式:local、standalone、yarn、mesos
      *  2.配置Spark Application名称
      *  3.可以设置Spark Application的资源【core+内存】
      */
    val conf = new SparkConf()
    conf.setMaster("local")
    conf.setAppName("scalaWc")

    /**
      * SparkContext是Spark的上下文，是通往集群的唯一入口
      */
    val sc = new SparkContext(conf)

    //val value: RDD[Int] = sc.parallelize(Array(1,2,3,4))
    val value2: RDD[String] = sc.makeRDD(List("a","b"))
    //sc.makeRDD(Set(1,2,3))

    val lines: RDD[String] = sc.textFile("./data/words")
    lines.persist(StorageLevel.MEMORY_ONLY)
    val pairWords = lines.flatMap(_.split(" ")).map(Tuple2(_,1))

    lines.take(3).foreach(println)
    val strings: Array[String] = lines.takeOrdered(3)
    println("___________________")
    strings.foreach(println)

//    val str: String = lines.reduce(_+" # "+_)
//    val tuple: (String, Int) = pairWords.reduce((tp1:Tuple2[String,Int], tp2:Tuple2[String,Int])=>Tuple2(tp1._1+tp2._1,tp1._2+tp2._2))

//    val sample = lines.sample(true,0.2)
//    val cartesian: RDD[(String, String)] = sample.cartesian(lines)
//    cartesian.foreach(println)

//    pairWords.combineByKey(s=>s,(v1:Int,v2:Int)=>v1+v2,(v3:Int,v4:Int)=>v3+v4).foreach(println)

//    val sample1 = pairWords.sample(true,0.08)
//    val sample2 = pairWords.sample(true,0.08)
//    val join: RDD[(String, (Int, Int))] = sample1.join(sample2)
//    val cogroup: RDD[(String, (Iterable[Int], Iterable[Int]))] = sample1.cogroup(sample2)
//    val cartesian: RDD[((String, Int), (String, Int))] = sample1.cartesian(sample2)
//
//    sample1.foreach(println)
//    println("___sample2____________________________________")
//    sample2.foreach(println)
//    println("____join___________________________________")
//    join.foreach(println)
//    println("___cogroup____________________________________")
//    cogroup.foreach(println)
//    println("___cartesian____________________________________")
//    cartesian.foreach(println)
//    println("_______________________________________")


//    val value: RDD[(String, Int)] = pairWords.aggregateByKey(0)(_+_,_-_)
//    val function: ((Unit, Int) => Unit, (Unit, Unit) => Unit) => RDD[(String, Unit)] = pairWords.aggregateByKey()
//
//    value.foreach(println)


//    val function: ((Unit, String) => Unit, (Unit, Unit) => Unit) => Unit = lines.aggregate()

    /*pairWords.groupByKey().foreach(println)
    pairWords.reduceByKey()*/

//    val value = lines.sample(true,0.3)

//    val distinct = value.distinct()
//    value.foreach(println)
//    distinct.foreach(println)

    /*val sample1 = lines.sample(true,0.1)
    val sample2 = lines.sample(true,0.1)

    val union: RDD[String] = sample1.union(sample2)
    val intersection = sample1.intersection(sample2)

    sample1.foreach(println)
    println("----------------------------")
    sample2.foreach(println)
    println("----------------------------")
    union.foreach(println)
    println("----------------------------")
    intersection.foreach(println)*/

//    val a = "a b"
//    val strings: Array[String] = a.split(" ")
//    val iterator: Iterator[String] = strings.iterator
//    val list: List[String] = strings.toList
//    val toarray: Array[String] = list.toArray
//    val iterator2 = List[String]("a","b").iterator
//    lines.mapPartitions(s=>Array(s).iterator)
//    lines.mapPartitions(s=>s)

    /*lines.groupBy(s=>s).foreach(println)
    println("######################################")
    lines.groupBy(s=>s.split(" "))
    println("######################################")*/

//    val pairWords = lines.flatMap(_.split(" ")).map(Tuple2(_,1))
//    pairWords.reduceByKey(_+_).foreach(println)
//    println("___________________________________________")
//    pairWords.groupByKey().foreach(println)
//    val reduce2: RDD[(String, Int)] = pairWords.reduceByKey(_+_)
//    val group: RDD[(String, Iterable[Int])] = pairWords.groupByKey()


//    lines.sample(true,0.1).foreach(println)
//    println("##########")
//    lines.filter(s=>s.equals("hello spark")).foreach(println)
    //println(lines.filter(s=>s.equals("hello spark")).count())

    sc.stop()
  }
}
