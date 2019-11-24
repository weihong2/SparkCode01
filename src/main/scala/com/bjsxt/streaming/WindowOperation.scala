package com.bjsxt.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object WindowOperation {
  def main(args: Array[String]): Unit = {
    val sc = new StreamingContext(new SparkConf().setMaster("local[2]").setAppName("test22"),Seconds(5))
    //sc.checkpoint()

    val dstream: ReceiverInputDStream[String] = sc.socketTextStream("node1",9999)
    val kvDS: DStream[(String, Int)] = dstream.flatMap(_.split(" ")).map((_,1))

    //一次计算15秒的数据，每个10秒计算一次
//    val result: DStream[(String, Int)] = kvDS.reduceByKeyAndWindow((v1:Int, v2:Int)=> v1+v2,Seconds(15),Seconds(10))
//    result.print()

    //优化后
//    sc.checkpoint("./data/reduceByKeyAndWindow")
//    val result = kvDS.reduceByKeyAndWindow((v1:Int,v2:Int)=>v1+v2,(v1:Int,v2:Int)=>v1-v2,Seconds(15),Seconds(10))
//    result.print()

    val ds2: DStream[(String, Int)] = kvDS.window(Seconds(15),Seconds(10))
    ds2.foreachRDD(rdd=>{})

    sc.start()
    sc.awaitTermination()
    sc.stop()
  }
}
