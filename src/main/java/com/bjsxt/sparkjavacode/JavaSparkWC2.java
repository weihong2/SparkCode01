package com.bjsxt.sparkjavacode;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

public class JavaSparkWC2 {
    public static void main(String[] args) {
        JavaSparkContext context = new JavaSparkContext(new SparkConf().setMaster("local").setAppName("WC2"));
        context.textFile("./data/words").
                flatMap(s -> Arrays.asList(s.split(" ")).iterator()).
                mapToPair(s -> new Tuple2<>(s,1)).
                reduceByKey((v1,v2) -> v1+v2).
                mapToPair(tp -> new Tuple2<>(tp._2,tp._1)).
                sortByKey(false).
                foreach(tp -> System.out.println(tp));

        //context.textFile("./data/words").flatMap()


        JavaRDD<String> lines = context.textFile("./data/words");
        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String line) throws Exception {
                return Arrays.asList(line.split(" ")).iterator();
            }
        });
        JavaPairRDD<String, Integer> pairWords = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<>(word, 1);
            }
        });
        JavaPairRDD<String, Integer> reduce = pairWords.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        });
        JavaPairRDD<Integer, String> swapPair = reduce.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> tuple2) throws Exception {
                return new Tuple2<>(tuple2._2, tuple2._1);
            }
        });
        JavaPairRDD<Integer, String> result = swapPair.sortByKey(false);
        result.foreach(new VoidFunction<Tuple2<Integer, String>>() {
            @Override
            public void call(Tuple2<Integer, String> integerStringTuple2) throws Exception {
                System.out.println(integerStringTuple2);
            }
        });

        context.stop();
    }
}
