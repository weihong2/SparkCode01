package com.bjsxt.sparkjavacode;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SparkBroadCast {
    public static void main(String[] args) {
        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setMaster("local").setAppName("test"));

        List<String> list = new ArrayList<>();
        list.add("zs");
        list.add("lisi");
        list.add("wangwu");
        Broadcast<List<String>> broadcast = sc.broadcast(list);

        sc.parallelize(Arrays.asList("zs", "lisi", "wangwu", "xiaoliu")).
                filter(s -> !broadcast.value().contains(s)).
                foreach(s-> System.out.println(s));

    }
}
