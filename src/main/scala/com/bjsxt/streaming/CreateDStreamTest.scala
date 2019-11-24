package com.bjsxt.streaming

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{InputDStream, ReceiverInputDStream}

import scala.collection.mutable

object CreateDStreamTest {
  def main(args: Array[String]): Unit = {
    val sc = new StreamingContext(new SparkConf().setMaster("local[2]").setAppName("test22"),Seconds(5))

    //val dstream: ReceiverInputDStream[String] = sc.socketTextStream("node1",9999)

    val context = sc.sparkContext
    val queue: mutable.Queue[RDD[Int]] = mutable.Queue[RDD[Int]]()
    for (i<- 1 to 10){
      queue.+=(context.makeRDD(Array(1,2,3,4,5,i)))
    }

    val dstream2: InputDStream[Int] = sc.queueStream(queue,false)
    dstream2.print()

    sc.start()
    sc.awaitTermination()
    sc.stop()
  }
}
