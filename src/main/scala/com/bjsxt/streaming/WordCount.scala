package com.bjsxt.streaming

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Durations, Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    //接收线程1个，处理线程n个，至少2个
    val conf = new SparkConf().setMaster("local[2]").setAppName("wc")
    //new SparkContext(conf).setLogLevel("ERROR")
    val sc = new StreamingContext(conf,Seconds(5))
    //sc.sparkContext.setLogLevel("Error")
    //val sc2 = new StreamingContext(conf,Durations.seconds(5))

    //sc.socketStream()
    val dStream: ReceiverInputDStream[String] = sc.socketTextStream("node1",9999)
    val result: DStream[(String, Int)] = dStream.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    result.print()

    sc.start()// 启动并且提交Job
    sc.awaitTermination()// 等待完成
    sc.stop()// 回收资源，不写系统帮助回收

    /*val ssc = new StreamingContext(new SparkConf().setMaster("local[2]").setAppName("test"),Seconds(5))
    val dstream: ReceiverInputDStream[String] = ssc.socketTextStream("node1",9999,StorageLevel.MEMORY_AND_DISK)
    val result2: DStream[(String, Int)] = dstream.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
    result.print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()*/
  }
}
