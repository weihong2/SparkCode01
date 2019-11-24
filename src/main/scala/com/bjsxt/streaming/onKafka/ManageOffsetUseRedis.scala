package com.bjsxt.streaming.onKafka

import java.{lang, util}

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}
import redis.clients.jedis.JedisPool

import scala.collection.mutable

/**
  * 手动维护offset
  *   1.从redis中读取offset
  *   2.传offset信息，消费数据
  *   3.返回一个批次的数据
  *   4.把消费的offset保存到redis
  */
object ManageOffsetUseRedis {


  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("test")
    conf.set("spark.streaming.kafka.maxRatePerPartition","10")
    val ssc = new StreamingContext(conf,Seconds(5))

    /**
      * 从redis中获取消费者offset
      *   offset存储结构：key：topic(  partition1：offset1 ,partition2：offset2 ,partition3：offset3 )
      */
    val topic = "topic"
    val dbIndex = 3
    val currentTopicOffset: mutable.Map[String, String] = getOffSetFromRedis(dbIndex,topic)

    val offsetsMap: mutable.Map[TopicPartition, Long] = currentTopicOffset.map(one => {
      new TopicPartition(topic, one._1.toInt) -> one._2.toLong
    })
    val fromOffsets: Predef.Map[TopicPartition, Long] = offsetsMap.toMap

    //kafka参数
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "node2:9092,node3:9092,node4:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "MyGroupId",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: lang.Boolean) //默认是true
    )

    /**
      * 将获取到的消费者offset 传递给SparkStreaming
      * 2.传offset信息给kafka，消费数据
      */
    val inputDS: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Assign[String, String](fromOffsets.keys.toList, kafkaParams, fromOffsets)
    )


  }

  def getOffSetFromRedis(dbIndex: Int, topic: String) = {
    val jedis = Redisclient.pool.getResource
    jedis.select(dbIndex)
    val result: util.Map[String, String] = jedis.hgetAll(topic)
    Redisclient.pool.returnResource(jedis)
    if(result.size()==0){ //第一次从redis取数据，初始化，所有分区的offset=0
      result.put("0","0")
      result.put("1","0")
      result.put("2","0")
    }
    //java的Map转换为Scala的Map
    import scala.collection.JavaConversions.mapAsScalaMap
    val offsetMap: scala.collection.mutable.Map[String, String] = result
    offsetMap
  }
}
object Redisclient{
  val redisHost = "node1"
  val redisPort = 6379
  val redisTimeout = 30000
  /**
    * JedisPool是一个连接池，既可以保证线程安全，又可以保证了较高的效率。
    */
  lazy val pool = new JedisPool(new GenericObjectPoolConfig(), redisHost, redisPort, redisTimeout)
}