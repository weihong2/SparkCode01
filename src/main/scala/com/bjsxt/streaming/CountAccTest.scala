package com.bjsxt.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.ReceiverInputDStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object CountAccTest {
  def main(args: Array[String]): Unit = {
    val sc = new StreamingContext(new SparkConf().setMaster("local[2]").setAppName("test22"),Seconds(5))

    val acc1 = sc.sparkContext.longAccumulator("acc1")
    //全局的累加器
    val inputDS: ReceiverInputDStream[String] = sc.socketTextStream("node1",9999)


    inputDS.foreachRDD(rdd=>{
      val acc2 = rdd.sparkContext.longAccumulator("acc2")
      rdd.foreach(line=>{
        if(line.contains("zs")){
          acc2.add(1)
        }
      })
      println(s"rdd2 = ${acc2.value}")
      acc1.add(acc2.value)
      println("全局---------："+acc1.value)
    })
    println("全局："+acc1.value)

    sc.start()
    sc.awaitTermination()
    println("全局2222："+acc1.value)
    sc.stop()
  }
}
