package com.bjsxt.streaming.onKafka

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.{Seconds, StreamingContext}
/**
* SparkStreaming2.3版本 读取kafka 中数据 ：
*  1.采用了新的消费者api实现，类似于1.6中SparkStreaming 读取 kafka Direct模式。并行度 一样。
*  2.因为采用了新的消费者api实现，所以相对于1.6的Direct模式【simple api实现】 ，api使用上有很大差别。未来这种api有可能继续变化
*  3.kafka中有两个参数：
*      heartbeat.interval.ms：这个值代表 kafka集群与消费者之间的心跳间隔时间，kafka 集群确保消费者保持连接的心跳通信时间间隔。
*             这个时间默认是3s.这个值必须设置的比session.timeout.ms 小，一般设置不大于 session.timeout.ms  的1/3
*      session.timeout.ms ：
*             这个值代表消费者与kafka之间的session 会话超时时间，如果在这个时间内，
*             kafka 没有接收到消费者的心跳【heartbeat.interval.ms 控制】，那么kafka将移除当前的消费者。这个时间默认是30s。
*             这个时间位于配置 group.min.session.timeout.ms【6s】 和 group.max.session.timeout.ms【300s】之间的一个参数,
*             如果SparkSteaming 批次间隔时间大于5分钟，也就是大于300s,那么就要相应的调大group.max.session.timeout.ms 这个值。
  *
*  4.大多数情况下，SparkStreaming读取数据使用 LocationStrategies.PreferConsistent 这种策略，这种策略会将分区均匀的分布在集群的Executor之间。
*    如果Executor在kafka 集群中的某些节点上，可以使用 LocationStrategies.PreferBrokers 这种策略，那么当前这个Executor 中的数据会来自当前broker节点。
*    如果节点之间的分区有明显的分布不均，可以使用 LocationStrategies.PreferFixed 这种策略,可以通过一个map 指定将topic分区分布在哪些节点中。
*
*  5.新的消费者api 可以将kafka 中的消息预读取到缓存区中，默认大小为64k。默认缓存区在 Executor 中，加快处理数据速度。
*     可以通过参数 spark.streaming.kafka.consumer.cache.maxCapacity 来增大，也可以通过spark.streaming.kafka.consumer.cache.enabled 设置成false 关闭缓存机制。
*     "注意：官网中描述这里建议关闭，在读取kafka时如果开启会有重复读取同一个topic partition 消息的问题，报错：KafkaConsumer is not safe for multi-threaded access"
* *
*  6.关于消费者offset
*    1).如果设置了checkpoint ,那么offset 将会存储在checkpoint中。可以利用checkpoint恢复offset ， getOrCreate 方法获取
*     这种有缺点: 第一，当从checkpoint中恢复数据时，有可能造成重复的消费。
*                第二，当代码逻辑改变时，无法从checkpoint中来恢复offset.
*    2).依靠kafka 来存储消费者offset,kafka 中有一个特殊的topic 来存储消费者offset。新的消费者api中，会定期自动提交offset。这种情况有可能也不是我们想要的，
*       因为有可能消费者自动提交了offset,但是后期SparkStreaming 没有将接收来的数据及时处理保存。这里也就是为什么会在配置中将enable.auto.commit 设置成false的原因。
*       这种消费模式也称最多消费一次，默认sparkStreaming 拉取到数据之后就可以更新offset,无论是否消费成功。自动提交offset的频率由参数auto.commit.interval.ms 决定，默认5s。
*       *如果我们能保证完全处理完业务之后，可以后期异步的手动提交消费者offset.
*       注意：这种模式也有弊端，这种将offset存储在kafka中方式，参数offsets.retention.minutes=1440控制offset是否过期删除，默认将offset信息保存一天，
*       如果停机没有消费达到时长，存储在kafka中的消费者组会被清空，offset也就被清除了。
*    3).自己存储offset,这样在处理逻辑时，保证数据处理的事务，如果处理数据失败，就不保存offset，处理数据成功则保存offset.这样可以做到精准的处理一次处理数据。
*
  * 7.auto.offset.reset 的参数
  *    earliest ：当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始
  *    latest：自动重置偏移量为最大偏移量【默认】*
  *    none:没有找到以前的offset,抛出异常
*/
object StreamingAndKafka {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("onkafka")
    //每一秒中从每一个topic的partition中最多读取多少条数据
    conf.set("spark.streaming.kafka.maxRatePerPartition","10")

    val ssc = new StreamingContext(conf,Seconds(5))

    /*
    * 设置消费者参数
    *    1.使用Kafka类型创建Map集合
    *    2.使用Java类型：Map、Properties
    * Kafka存储数据格式：
    *    1.K，V
    *    2.V
    * */
    val kafkaProperties: Map[String, Object] = Map[String, Object](
      "group.id" -> "mygroup1", //消费组的名称
      "bootstrap.servers" -> "node2:9092,node3:9092,node4:9092", //Kafka集群
      "key.deserializer" -> classOf[StringDeserializer], //消费数据需要反序列
      "value.deserializer" -> classOf[StringDeserializer],
      "auto.offset.reset" -> "earliest", //第一次读从0开始，以后从当前offset开始
      "offset.auto.commit" -> (false:java.lang.Boolean) //是否自动提交offest给zookeeper
    )
    //定义要读取的topic的名称，注意：使用集合类型
    val topics = Array("t0725")

    //从 Kafka中读取一个批次的数据
    val dstream: InputDStream[ConsumerRecord[String, String]] = KafkaUtils.createDirectStream(
      ssc,
      LocationStrategies.PreferConsistent, //PreferConsistent：每一个executor对应一个topic的partition
      ConsumerStrategies.Subscribe[String, String](topics, kafkaProperties))

    //处理，计算，对 ConsumerRecord的value进行 WC
    val result: DStream[(String, Int)] = dstream.map(_.value()).flatMap(_.split("\t")).map((_,1)).reduceByKey(_+_)

    //更新offset，并且保存到 Kafka中
    dstream.foreachRDD(rdd=>{

      //OffsetRange (1:1) partition的offset信息
      val offsetRanges: Array[OffsetRange] = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      for (ors <- offsetRanges){
        println(s"当前读取的topic：${ors.topic} ,当前partition是：${ors.partition} ,当前起始的offset：${ors.fromOffset} ,结束的offset：${ors.untilOffset}")
      }

      //提交到 kafka中
      dstream.asInstanceOf[CanCommitOffsets].commitAsync(offsetRanges)
    })

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}
