package com.bjsxt.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Driver HA ：
  * 1.在提交application的时候  添加 --supervise 选项  如果Driver挂掉 会自动启动一个Driver
  * 2.代码层面恢复Driver(StreamingContext)
  *
  */
object SparkStreamingDriverHA {
  val dirverDir = "./data/streamingCheckpoint"

  def main(args: Array[String]): Unit = {
    val ssc = StreamingContext.getActiveOrCreate(dirverDir,createStreamingContext)
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }

  def createStreamingContext() = {
    val ssc = new StreamingContext(new SparkConf().setMaster("local[2]").setAppName("driverHA"),Seconds(5))

    ssc.checkpoint(dirverDir)
    val lines = ssc.textFileStream("./data/streamingCopyFile")
    val result = lines.flatMap(_.trim.split(" ")).map((_,1)).reduceByKey(_+_)
    result.print()

    ssc
  }

  /*val ckDir = "./data/streamingCheckpoint"

  def main(args: Array[String]): Unit = {
    val ssc = StreamingContext.getActiveOrCreate(ckDir,CreateStreamingContext)
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }

  def CreateStreamingContext() = {
    println("-----Create new StreamingContext-----")
    val ssc: StreamingContext = new StreamingContext(new SparkConf().setMaster("local[2]").setAppName("DriverHA"),Seconds(5))

    ssc.checkpoint(ckDir)
    val lines: DStream[String] = ssc.textFileStream("./data/streamingCopyFile")
    val result: DStream[(String, Int)] = lines.flatMap(_.trim.split(" ")).map((_,1)).reduceByKey(_+_)
    result.print()

    ssc
  }*/
}
