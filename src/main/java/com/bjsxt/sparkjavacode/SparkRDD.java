package com.bjsxt.sparkjavacode;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class SparkRDD {
    public static void main(String[] args) {
        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setMaster("local").setAppName("test"));

        //这是java的 K，V的RDD
        JavaRDD<Tuple2<String, Integer>> javaRDD = sc.parallelize(Arrays.asList(new Tuple2<>("a", 2), new Tuple2<>("b", 4)));

        JavaPairRDD<String, Integer> pairRDD = sc.parallelizePairs(Arrays.asList(new Tuple2<>("a", 2), new Tuple2<>("b", 4)));

//        JavaRDD<Integer> parallelize = sc.parallelize(Arrays.asList(1, 2, 3, 4, 5, 5));
//        parallelize.foreach(s-> System.out.println(s));
        //sc.parallelizePairs()


        JavaRDD<String> lines = sc.textFile("./data/words");

        JavaPairRDD<String, Integer> pairRDD1 = lines.flatMap(s -> Arrays.asList(s.split(" ")).iterator()).mapToPair(s -> new Tuple2<>(s, 1));

        Tuple2<String, Integer> reduce = pairRDD1.reduce(new Function2<Tuple2<String, Integer>, Tuple2<String, Integer>, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<String, Integer> t1, Tuple2<String, Integer> t2) throws Exception {
                return new Tuple2<>(t1._1 + "+" + t2._1, t1._2 + t2._2);
            }
        });
        System.out.println(reduce);

//        lines.groupBy()

//        String reduce = lines.reduce((s1, s2) -> s1 + " # " + s2);
//        long count = lines.count();
//        List<String> take = lines.take(3);
//        String first = lines.first();
//        List<String> collect = lines.collect();//scala是Array

        //JavaRDD<String> sample = lines.sample(true, 0.1);
        /*lines.filter(new Function<String, Boolean>() {
            @Override
            public Boolean call(String s) throws Exception {
                return null;
            }
        })*/

        sc.stop();
    }
}
