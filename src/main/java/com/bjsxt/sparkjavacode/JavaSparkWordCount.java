package com.bjsxt.sparkjavacode;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.*;
import org.stringtemplate.v4.ST;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

public class JavaSparkWordCount {
    public static void main(String[] args) {
        /*SparkConf conf = new SparkConf();
        conf.setMaster("local");
        conf.setAppName("javawordcount");

        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaRDD<String> lines = sc.textFile("./data/words");*/

        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setMaster("local").setAppName("javaWordCount"));

        sc.textFile("./data/words").flatMap(s -> Arrays.asList(s.split(" ")).iterator()).mapToPair(s -> new Tuple2<>(s,1)).
                reduceByKey((Integer v1,Integer v2)->v1+v2).foreach(s -> System.out.println(s));

        System.out.println("######################");

        sc.textFile("./data/words").
                flatMap(s -> Arrays.asList(s.split(" ")).iterator()).
                mapToPair(s -> new Tuple2<>(s,1)).
                reduceByKey((v1,v2)->v1+v2).
                mapToPair(t -> new Tuple2<>(t._2,t._1)).sortByKey(false).
                foreach(System.out::println);

        Arrays.asList(10.0,20.0,30.0).stream().map(x -> x+0.5).forEach(s -> System.out.println(s));
        Optional<Double> reduce = Arrays.asList(10.0, 20.0, 30.0).stream().map(x -> x + 0.5).reduce((sum, x) -> sum + x);
        System.out.println(reduce.get());


        /*JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                return Arrays.asList(s.split(" ")).iterator();
            }
        });

        JavaPairRDD<String, Integer> pairWords = words.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        });

        JavaPairRDD<String, Integer> reduce = pairWords.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer+integer2;
            }
        });

        JavaPairRDD<Integer, String> swapPairWord = reduce.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> tuple2) throws Exception {
                return new Tuple2<>(tuple2._2, tuple2._1);
            }
        });

        JavaPairRDD<Integer, String> sortPairWord = swapPairWord.sortByKey(false);

        sortPairWord.foreach(new VoidFunction<Tuple2<Integer, String>>() {
            @Override
            public void call(Tuple2<Integer, String> integerStringTuple2) throws Exception {
                System.out.println(integerStringTuple2);
            }
        });*/



        /*JavaRDD<Tuple2<String, Integer>> tupleWord = words.map(new Function<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        });

        Tuple2<String, Integer> reduce1 = tupleWord.reduce(new Function2<Tuple2<String, Integer>, Tuple2<String, Integer>, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<String, Integer> tuple2, Tuple2<String, Integer> tuple22) throws Exception {
                System.out.println(tuple2.toString()+" ## "+tuple22.toString());
                if(tuple2._1.equals(tuple22._1)){
                    return new Tuple2<>(tuple2._1, tuple2._2 + tuple22._2);
                }
                return new Tuple2<>(tuple22._1, tuple2._2 + tuple22._2);
            }
        });
        System.out.println("-------------");
        System.out.println(reduce1.toString());
        System.out.println("-------------");*/

        sc.stop();

    }
}
