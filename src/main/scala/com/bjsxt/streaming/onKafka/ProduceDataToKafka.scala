package com.bjsxt.streaming.onKafka

import java.text.SimpleDateFormat
import java.util
import java.util.{Date, Properties}

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

import scala.util.Random

object ProduceDataToKafka {
  def main(args: Array[String]): Unit = {
    val properties = new Properties()
    properties.setProperty("bootstrap.servers","node2:9092,node3:9092,node4:9092")
    //properties.setProperty("key.serializer",StringSerializer)
    properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer")
    properties.setProperty("value.serializer","org.apache.kafka.common.serialization.StringSerializer")

    val producer2: KafkaProducer[String, String] = new KafkaProducer[String,String](properties)

    producer2.send(new ProducerRecord[String,String]("topic","key","value"))


    /*val props = new Properties()
    props.put("bootstrap.servers", "node2:9092,node3:9092,node4:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String,String](props)
    var counter = 0
    var keyFlag = 0
    while(true){
      counter +=1
      keyFlag +=1
      val content: String = userlogs()
      producer.send(new ProducerRecord[String, String]("t0725", s"key-$keyFlag", content))
      if(0 == counter%100){
        counter = 0
        Thread.sleep(5000)
      }
    }

    producer.close()*/
  }

  def userlogs()={
    val userLogBuffer = new StringBuffer("")
    val timestamp = new Date().getTime();
    var userID = 0L
    var pageID = 0L

    //随机生成的用户ID
    userID = Random.nextInt(2000)

    //随机生成的页面ID
    pageID =  Random.nextInt(2000);

    //随机生成Channel
    val channelNames = Array[String]("Spark","Scala","Kafka","Flink","Hadoop","Storm","Hive","Impala","HBase","ML")
    val channel = channelNames(Random.nextInt(10))

    val actionNames = Array[String]("View", "Register")
    //随机生成action行为
    val action = actionNames(Random.nextInt(2))

    val dateToday = new SimpleDateFormat("yyyy-MM-dd").format(new Date())
    userLogBuffer.append(dateToday)
      .append("\t")
      .append(timestamp)
      .append("\t")
      .append(userID)
      .append("\t")
      .append(pageID)
      .append("\t")
      .append(channel)
      .append("\t")
      .append(action)
    System.out.println(userLogBuffer.toString())
    userLogBuffer.toString()
  }
}
