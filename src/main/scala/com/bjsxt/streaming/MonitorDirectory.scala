package com.bjsxt.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Durations, Seconds, StreamingContext}

/**
  * 监控某个目录下的文件，这个文件必须是原子性的在目录中产生，
  *   已经存在的文件后面追加数据不能被监控到，被删除的文件也不能被监控到
  *   只能监控该目录下的IO操作，创建文件的操作
  */
object MonitorDirectory {
  def main(args: Array[String]): Unit = {
    //监控某个文件夹时，不需要设置local[2],没有采用 receiver 接收器的模式读取数据
    val conf = new SparkConf().setMaster("local").setAppName("wc")
    val ssc = new StreamingContext(conf,Seconds(5))

    val lines: DStream[String] = ssc.textFileStream("./data/streamingCopyFile")
    val words: DStream[String] = lines.flatMap(line=>{line.trim.split(" ")})
    val pairWords: DStream[(String, Int)] = words.map(word=>{(word.trim,1)})
    val result: DStream[(String, Int)] = pairWords.reduceByKey((v1, v2)=>{v1+v2})
    result.print()
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}
