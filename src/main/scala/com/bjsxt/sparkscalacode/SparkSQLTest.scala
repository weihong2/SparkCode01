package com.bjsxt.sparkscalacode

import org.apache.spark.rdd.RDD
import org.apache.spark.sql._
import org.apache.spark.{SparkConf, SparkContext}

object SparkSQLTest {
  def main(args: Array[String]): Unit = {
    /*val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("test"))
    val sQLContext = new SQLContext(sc)
    val frame: DataFrame = sQLContext.read.json("")*/


    //getOrCreate()看上下文是否有session，已有拿来包装，没有创建
    val session = SparkSession.builder().master("local").appName("test").getOrCreate()
    session.sparkContext.setLogLevel("Error")

    val frame: DataFrame = session.read.json("./data/json")
    val frame2: Dataset[Row] = session.read.json("./data/json")

    frame.printSchema()
    frame.show()//默认20行，可指定show(100)


    val rdd: RDD[Row] = frame.rdd//Row理解为数组
    rdd.foreach(println)
    rdd.foreach(row=>{
      //val unit: Any = row.get(0)
      val name: String = row.getAs[String]("name")
      val age: Long = row.getAs[Long]("age")
      println(s"name is $name , age is $age")
    })


    /**
      * 注册临时视图，写sql，将DataFrame注册成视图
      */
    //frame.registerTempTable("temp")
    frame.createTempView("temp")
    frame.createOrReplaceTempView("aaa")//覆盖
    val frame22: DataFrame = session.sql("select count(*) from temp")
    frame22.show()

    frame.createOrReplaceGlobalTempView("bbb")//跨session访问
    frame.createGlobalTempView("ccc")
    val session2 = session.newSession()
    session2.sql("select * from global_temp.bbb").show()

    session.sql("select name,age from temp where name like 'zhangsan%'").show()

    /**
      * DataFrame API
      */

    //select name,age from temp
    val frame3: DataFrame = frame.select("name","age")
    frame3.show()

    //select name,age from tmp where age>20
    val unit: Dataset[Row] = frame.select("name","age").filter("age > 20")
    unit.show()

    //select name,age from tmp where name like "zhangsan%"
    //select name,age from tmp where name = "zhangsan2"
    frame.select("name","age").filter("name = 'zhangsan2'").show()

    //select count(*) from tmp where age > 18
    frame.filter("age > 18").count()

    //select age,count(*) from tmp group by age
    frame.filter("age is not null").groupBy("age").count().show()
  }
}
