package com.bjsxt.sparkjavacode;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.sql.SparkSession;
import scala.Tuple2;

public class SparkPVUV {
    public static void main(String[] args) {
        SparkSession sparkSession = SparkSession.builder().master("local").appName("test").getOrCreate();
        JavaSparkContext sc = new JavaSparkContext(sparkSession.sparkContext());

        JavaRDD<String> lines = sc.textFile("./data/pvuvdata");

        /**
         * pv
         */
        lines.mapToPair(s->new Tuple2<>(s.split("\t")[5],1)).
                reduceByKey((a,b)->a+b).mapToPair(t->t.swap()).
                sortByKey(false).mapToPair(t->t.swap()).foreach(s-> System.out.println(s));
        /**
         * uv
         */
        lines.map(s->s.split("\t")[0]+"_"+s.split("\t")[5]).
                distinct().mapToPair(s->new Tuple2<>(s.split("_")[1],1)).
                reduceByKey((a,b)->a+b).mapToPair(t->t.swap()).
                sortByKey(false).mapToPair(t->t.swap()).foreach(s-> System.out.println(s));

        /**
         * uv
         */
        lines.map(new Function<String, String>() {
            @Override
            public String call(String s) throws Exception {
                return s.split("\t")[0]+"_"+s.split("\t")[5];
            }
        }).distinct().mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String,Integer>(s.split("_")[1],1);
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer+integer2;
            }
        }).mapToPair(new PairFunction<Tuple2<String,Integer>, Integer, String>() {
            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> tuple2) throws Exception {
                return tuple2.swap();
            }
        }).sortByKey(false).mapToPair(new PairFunction<Tuple2<Integer,String>, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<Integer, String> integerStringTuple2) throws Exception {
                return integerStringTuple2.swap();
            }
        }).foreach(one-> System.out.println(one));

        /**
         * pv
         */
        System.out.println("____________________");
        lines.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s.split("\t")[5], 1);
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) throws Exception {
                return integer+integer2;
            }
        }).mapToPair(new PairFunction<Tuple2<String,Integer>, Integer, String>() {
            @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> tuple2) throws Exception {
                return tuple2.swap();
            }
        }).sortByKey(false).mapToPair(new PairFunction<Tuple2<Integer,String>, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(Tuple2<Integer, String> integerStringTuple2) throws Exception {
                return integerStringTuple2.swap();
            }
        }).foreach(one-> System.out.println(one));

    }
}
