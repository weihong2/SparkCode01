package com.bjsxt.sparkjavacode;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;

public class SparkCache {
    public static void main(String[] args) {
        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setMaster("local").setAppName("test"));

        JavaRDD<String> lines = sc.textFile("./data/words");

        JavaRDD<String> persist = lines.persist(StorageLevel.MEMORY_ONLY());

        long timeMillis = System.currentTimeMillis();
        long count = persist.count();
        long endtimeMillis = System.currentTimeMillis();
    }
}
