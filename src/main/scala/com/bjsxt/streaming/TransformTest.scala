package com.bjsxt.streaming

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object TransformTest {
  def main(args: Array[String]): Unit = {
    val sc = new StreamingContext(new SparkConf().setMaster("local[2]").setAppName("test22"),Seconds(5))

    /**
      * 有状态的转换算子必须设置checkpoint目录，为了保存之前批次计算结果
      * 默认：当batchInterval < 10秒，每10秒往checkpoint目录持久化一次
      *       大于10秒，那么就以bacthInterval为准
      *     这样做是为了防止频繁的写HDFS
      */
    sc.checkpoint("./data/updateStateByKey")

    val dStream: ReceiverInputDStream[String] = sc.socketTextStream("node1",9999)
    /*val unit = dStream.transform(rdd => {
      val rdd2: RDD[(String, Int)] = rdd.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
      rdd2
    })*/

    //参数：(前缀：目录，后缀)，保存到文件中
    //dStream.saveAsTextFiles("","")

    val kvDS: DStream[(String, Int)] = dStream.flatMap(_.split(" ")).map((_,1))

    /**
      * 将之前批次计算的结果和当前批次的数据相加，计算总的单词的个数
      * updateStateByKey：
      *     首先把当前批次计算结果按照key分组（类似groupByKey）
      * 参数：seq：集合，当前批次计算的结果值的集合（类似groupByKey的结果value=Iterator[V]）
      *      opt：之前批次计算结果中是否有当前key的值
      */
    val result: DStream[(String, Int)] = kvDS.updateStateByKey[Int]((seq:Seq[Int], opt: Option[Int]) => {
      var sum = 0
      if (!opt.isEmpty) {
        sum = opt.get
      }
      seq.foreach(sum += _)
      Option(sum)
    })
    result.print()

    sc.start()
    sc.awaitTermination()
    sc.stop()
  }
}
