package com.bjsxt.sparkjavacode;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

public class SparkSecondSort {
    public static void main(String[] args) {
        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setMaster("local").setAppName("test"));

        JavaRDD<String> lines = sc.textFile("./data/secondSort.txt");

        lines.mapToPair(s->new Tuple2<MySort,String>(new MySort(Integer.valueOf(s.split(" ")[0]),Integer.valueOf(s.split(" ")[1])),s)).
                sortByKey(false).foreach(s-> System.out.println(s._2));


        lines.mapToPair(new PairFunction<String, MySort, String>() {
            @Override
            public Tuple2<MySort, String> call(String s) throws Exception {
                return new Tuple2<>(new MySort(Integer.valueOf(s.split(" ")[0]),Integer.valueOf(s.split(" ")[1])),s);
            }
        }).sortByKey(false).foreach(new VoidFunction<Tuple2<MySort, String>>() {
            @Override
            public void call(Tuple2<MySort, String> mySortStringTuple2) throws Exception {
                System.out.println(mySortStringTuple2._2);
            }
        });
    }
}
