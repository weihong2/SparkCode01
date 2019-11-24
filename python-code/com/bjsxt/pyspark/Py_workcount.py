from pyspark import SparkConf, SparkContext

if __name__ == '__main__':
    conf = SparkConf().setMaster("local").setAppName("wc")
    sc = SparkContext(conf=conf)

    file_rdd = sc.textFile("./data1")
    result_rdd = file_rdd.flatMap(lambda line:line.split(" "))\
        .map(lambda w:(w,1)).reduceByKey(lambda x1,x2:x1+x2)
    result_rdd.foreach(print)