package com.bjsxt.sparkjavacode;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.*;

public class TransformationTest3 {
    public static void main(String[] args) {
        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setMaster("local").setAppName("test"));

        JavaRDD<Tuple2<String, Integer>> kvRDD = sc.parallelize(Arrays.asList(
                new Tuple2<String, Integer>("zs", 18),
                new Tuple2<String, Integer>("lisi", 46),
                new Tuple2<String, Integer>("wangwu", 27),
                new Tuple2<String, Integer>("zs", 33),
                new Tuple2<String, Integer>("zs", 18)
        ));

        JavaRDD<String> rdd1 = sc.parallelize(Arrays.asList(
                "love1", "love2", "love3", "love4",
                "love1", "love2", "love3", "love4",
                "love5", "love6", "love7", "love8",
                "love9", "love10", "love12", "love13"
        ), 3);

        JavaPairRDD<String, Iterable<String>> groupBy = rdd1.groupBy(s -> s+" & ");
        //groupBy.foreach(s-> System.out.println(s));
        JavaPairRDD<String, Iterable<Tuple2<String, Integer>>> groupBy1 = kvRDD.groupBy(s -> s + " # ");
        //groupBy1.foreach(s-> System.out.println(s));

        Map<String, Long> map = rdd1.countByValue();



//        JavaRDD<String> rdd11 = rdd1.repartition(4);
//
//        JavaRDD<String> coalesce = rdd1.coalesce(2);


//        JavaRDD<String> rdd2 = rdd1.mapPartitionsWithIndex(new Function2<Integer, Iterator<String>, Iterator<String>>() {
//            @Override
//            public Iterator<String> call(Integer index, Iterator<String> iter) throws Exception {
//                List list = new ArrayList<String>();
//                while(iter.hasNext()){
//                    list.add("partition index = "+index + ",value = "+iter.next());
//                    list.add("rdd1 partition idex = {" + index + "} , value = {{"+iter.next()+"}}");
//                }
//                return list.iterator();
//            }
//        }, true);
//        rdd2.foreach(s-> System.out.println(s));
    }
}
