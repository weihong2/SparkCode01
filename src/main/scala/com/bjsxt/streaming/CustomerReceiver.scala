package com.bjsxt.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object CustomerReceiver {
  def main(args: Array[String]): Unit = {
    val sc = new StreamingContext(new SparkConf().setMaster("local[2]").setAppName("wc"),Seconds(5))
    sc.sparkContext.setLogLevel("Error")

    val dStream: ReceiverInputDStream[String] = sc.receiverStream[String](new MyReceiver("node1",9999))
//    dStream.print()
    //dStream.foreachRDD(_.foreach(println))
    dStream.foreachRDD(rdd=>{
      println("执行foreachRDD")
      
    })

    sc.start()
    sc.awaitTermination()
    sc.stop()
  }
}
