package com.bjsxt.sparkjavacode;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.RuntimeConfig;
import org.apache.spark.sql.SparkSession;

public class SparkCheckpointJava {
    public static void main(String[] args) {
        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setMaster("local").setAppName("che"));

        sc.setCheckpointDir("./ck");

        JavaRDD<String> lines = sc.textFile("./data/words");

        lines.checkpoint();

        lines.count();

        sc.stop();

        //conf.set("xx","xx")--->SparkSession.builder().config("xx","xx")
        SparkSession session = SparkSession.builder().master("local").appName("test").getOrCreate();
        SparkContext sc2 = session.sparkContext();
        RuntimeConfig conf = session.conf();
    }
}
