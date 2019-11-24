package com.bjsxt.sparkjavacode;

import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

public class SparkAccumulator {
    public static void main(String[] args) {
        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setMaster("local").setAppName("test"));

        Accumulator<Integer> accumulator = sc.accumulator(1);
        Accumulator<Integer> intAccumulator = sc.intAccumulator(1);

        JavaRDD<String> lines = sc.textFile("./data/words", 2);

        lines.map(new Function<String, String>() {
            @Override
            public String call(String s) throws Exception {
                accumulator.add(1);
                intAccumulator.add(1);
                return s;
            }
        }).count();
        System.out.println("accumulator is "+accumulator);
        System.out.println("accumulator is "+intAccumulator);

    }
}
