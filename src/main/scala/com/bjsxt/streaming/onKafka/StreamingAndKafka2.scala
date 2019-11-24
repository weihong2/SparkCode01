package com.bjsxt.streaming.onKafka

import java.{io, lang}

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Durations, StreamingContext}

object StreamingAndKafka2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("test")
    conf.set("spark.streaming.kafka.maxRatePerPartition","10")
    val ssc = new StreamingContext(conf,Durations.seconds(5))

    //设置消费者参数
    val consumerParams: Map[String, io.Serializable] = Map(
      "group.id" -> "mygroup1",
      "bootstrap.servers" -> "node2:9092,node3:9092,node4:9092",
      "key.desrializer" -> classOf[StringDeserializer],
      "value.desrializer" -> classOf[StringDeserializer],
      "auto.offset.reset" -> "earliest",
      "offset.auto.commit" -> (false:lang.Boolean)
    )

    //从 Kafka中读取一个批次的数据offset
    val inputDS: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](Array("topics"), consumerParams)
    )
    val resutl = inputDS.map(_.value())

    //更新offset，并且保存到 Kafka中
    inputDS.foreachRDD(rdd=>{
      inputDS.asInstanceOf[CanCommitOffsets].commitAsync(rdd.asInstanceOf[HasOffsetRanges].offsetRanges)
    })


    /*val consumerCanShu: Map[String, Object] = Map(
      "group.id" -> "mygroup1", //消费组的名称
      "bootstrap.servers" -> "node2:9092,node3:9092,node4:9092", //Kafka集群
      "key.deserializer" -> classOf[StringDeserializer], //消费数据需要反序列
      "value.deserializer" -> classOf[StringDeserializer],
      "auto.offset.reset" -> "earliest", //第一次读从0开始，以后从当前offset开始
      "offset.auto.commit" -> (false:java.lang.Boolean) //是否自动提交offest给zookeeper
    )

    val inputDS: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](Array("topics"), consumerCanShu))

    val result: DStream[(String, Int)] = inputDS.map(_.value()).flatMap(_.split("\t")).map((_,1)).reduceByKey(_+_)

    inputDS.foreachRDD(rdd=>{
      val ranges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges

      for (ors <- ranges){
        println(s"当前读取的topic：${ors.topic} ,当前partition是：${ors.partition} ,当前起始的offset：${ors.fromOffset} ,结束的offset：${ors.untilOffset}")
      }

      inputDS.asInstanceOf[CanCommitOffsets].commitAsync(ranges)
    })*/

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}
