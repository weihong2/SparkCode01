package com.bjsxt.sparkjavacode;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.Optional;
import org.omg.PortableInterceptor.INACTIVE;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;

public class SparkTransfromation2 {
    public static void main(String[] args) {
        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setMaster("local").setAppName("test"));
        //sc.setLogLevel();

        JavaRDD<Integer> rdd1 = sc.parallelize(Arrays.asList(1, 2,2, 3, 4, 5));
        JavaRDD<Integer> rdd2 = sc.parallelize(Arrays.asList(1, 2, 3,3, 8, 9));

        JavaRDD<Integer> union = rdd1.union(rdd2);
        JavaRDD<Integer> intersection = rdd1.intersection(rdd2);
        JavaRDD<Integer> distinct = union.distinct();
        JavaRDD<Integer> subtract = rdd1.subtract(rdd2);

        rdd1.foreach(s-> System.out.println(s));
        JavaRDD<Integer> intersection1 = rdd1.intersection(rdd1);
        intersection1.foreach(s-> System.out.println(s));

        JavaRDD<String> javaRDD = sc.parallelize(Arrays.asList("a", "b", "c", "a", "d"));

        javaRDD.foreachPartition(stringIterator -> {
            ArrayList<Object> list = new ArrayList<>();
            System.out.println("连接数据库");
            while (stringIterator.hasNext()){
                list.add(stringIterator.next());
            }
            System.out.println(list.toString());
            System.out.println("关闭-----");
        });
        JavaRDD<Object> rdd = javaRDD.mapPartitions(stringIterator -> {
            ArrayList<Object> list = new ArrayList<>();
            System.out.println("连接数据库");
            while (stringIterator.hasNext()) {
                list.add(stringIterator.next());
            }
            System.out.println(list.toString());
            System.out.println("关闭-----");
            return list.iterator();
        });

//        JavaPairRDD<String, Integer> pair = javaRDD.mapToPair(s -> new Tuple2<>(s, 1));
//        JavaPairRDD<String, Integer> reduce = pair.reduceByKey((a, b) -> a + b);
//        JavaRDD<String> map = reduce.map(tuple2 -> tuple2._1);
//        map.foreach(s-> System.out.println(s));
//
//        JavaRDD<String> map1 = javaRDD.map(s -> s + "#");
//        map1.foreach(s-> System.out.println(s));

        /*union.foreach(s-> System.out.println(s));
        System.out.println("________________________");
        intersection.foreach(s-> System.out.println(s));
        System.out.println("________________________");
        distinct.foreach(s-> System.out.println(s));
        System.out.println("________________________");
        subtract.foreach(s-> System.out.println(s));*/

        /*JavaPairRDD<String, Integer> nameRDD = sc.parallelizePairs(Arrays.asList(
                new Tuple2<String, Integer>("zs", 23),
                new Tuple2<String, Integer>("lisi", 14),
                new Tuple2<String, Integer>("wangwu", 34),
                new Tuple2<String, Integer>("xiaoliu", 10),
                new Tuple2<String, Integer>("sxt", 44)
        ));
        JavaPairRDD<String, Integer> scoreRDD = sc.parallelizePairs(Arrays.asList(
                new Tuple2<String, Integer>("zs", 99),
                new Tuple2<String, Integer>("lisi", 60),
                new Tuple2<String, Integer>("wangwu", 78),
                new Tuple2<String, Integer>("xiaoliu", 44),
                new Tuple2<String, Integer>("sxt22", 88)
        ));

        JavaPairRDD<String, Tuple2<Integer, Integer>> join = nameRDD.join(scoreRDD);

        JavaPairRDD<String, Tuple2<Iterable<Integer>, Iterable<Integer>>> cogroup = nameRDD.cogroup(scoreRDD);

        JavaPairRDD<String, Tuple2<Integer, Optional<Integer>>> javaPairRDD = nameRDD.leftOuterJoin(scoreRDD);

        JavaPairRDD<String, Tuple2<Optional<Integer>, Integer>> rightOuterJoin = nameRDD.rightOuterJoin(scoreRDD);

        JavaPairRDD<String, Tuple2<Optional<Integer>, Optional<Integer>>> fullOuterJoin = nameRDD.fullOuterJoin(scoreRDD);

        System.out.println(nameRDD.getNumPartitions());

        join.foreach(s-> System.out.println(s));
        System.out.println("_____________________________________");
        javaPairRDD.foreach(s-> {
            Optional<Integer> tp = s._2._2;
            if(tp.isPresent()){
                System.out.println("key = "+s._1+",value1="+s._2._1+",value2="+tp.get());
            }else {
                System.out.println("key = "+s._1+",value1="+s._2._1+",value2= Null");
            }
        });
        System.out.println("_____________________________________");
        cogroup.foreach(s-> System.out.println(s));*/

        sc.stop();
    }
}
