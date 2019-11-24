package com.bjsxt.sparkjavacode;

import com.sun.org.apache.bcel.internal.generic.SWAP;
import org.apache.commons.collections.IteratorUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Array;
import scala.Int;
import scala.Tuple2;

import java.util.*;

public class SparkGroup1 {
    public static void main(String[] args) {
        JavaSparkContext sc = new JavaSparkContext(new SparkConf().setMaster("local").setAppName("test"));

        JavaRDD<String> lines = sc.textFile("./data/scores.txt");

        lines.mapToPair(s->new Tuple2<>(s.split("\t")[0],Integer.valueOf(s.split("\t")[1]))).
                groupByKey().foreach(s->{
            String key = s._1;
            Iterator<Integer> iter = s._2.iterator();
            int[] ints = new int[3];
            while (iter.hasNext()){
                Integer next = iter.next();
                for (int i = 0; i < ints.length; i++) {
                    if (next>ints[i]){
                        for (int j = ints.length-1; j > i; j--) {
                            ints[j] = ints[j-1];
                        }
                        ints[i] = next;
                        break;
                    }
                }
            }
            for (Integer i :
                    ints) {
                System.out.println("key = "+key+" value = "+i);
            }

            /*//原生集合排序

//            Iterable<Integer> iter = s._2;
//            ArrayList<Integer> list = new ArrayList<>();
//            for (Integer i:iter){
//                list.add(i);
//            }
            Iterator<Integer> iter = s._2.iterator();
            List<Integer> list = IteratorUtils.toList(iter);
            Collections.sort(list, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2-o1;
                }
            });
            for (Integer i : list){
                System.out.println("key = "+key+" value = "+i);
            }*/
        });

        System.out.println("+++++++++++++++++++++++");
        lines.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<>(s.split("\t")[0],Integer.valueOf(s.split("\t")[1]));
            }
        }).groupByKey().foreach(s->{

            //原生集合
            List list = IteratorUtils.toList(s._2.iterator());
            Collections.sort(list, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o2-o1;
                }
            });
            int i = 0;
            System.out.println("class = "+s._1);
            for (Iterator iter2 = list.iterator(); iter2.hasNext()&&i<3 ; i++) {
                System.out.println(iter2.next());
            }
        });
    }
}
