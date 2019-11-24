from pyspark import SparkConf, SparkContext

#每一行数据包括：时间，客户端IP，用户ID，地域信息city，访问的网址，用户操作
def get_top3_local(one):
    # one=(site,[local,local,....])
    site = one[0]
    local_iterable = one[1]
    local_dic = {}
    for local in local_iterable:
        if local in local_dic:
            local_dic[local] +=1
        else:
            local_dic[local] =1

    # .items()将字典=>二元组的列表
    newlist = sorted(local_dic.items(), key = lambda tp:tp[1],reverse=True)
    returnlist = []
    if len(newlist)>3:
        returnlist = newlist[:3]
        # for i in range(0,3):
        #     returnlist.append(newlist[i])
    else:
        returnlist = newlist
    return site,returnlist


def uid_sites_site_uid_shu(one):
    # one=(uid,[site,site,....]) -> (site,(uid,count))
    uid = one[0]
    sites_iterable = one[1]
    siteDic = {}
    for site in sites_iterable:
        if site in siteDic:
            siteDic[site] +=1
        else:
            siteDic[site] = 1
    returnList = []
    for site,count in siteDic.items():
        returnList.append((site,(uid,count)))
    return returnList


def getTopsUid(one):
    # one=(site,[(uid,count),(uid,count),...])
    site = one[0]
    uid_count_iterable = one[1]
    top3List = ['','','']
    for uid_count in uid_count_iterable:
        uid = uid_count[0]
        count = uid_count[1]
        for i in range(len(top3List)):
            if(top3List[i]==''):
                top3List[i] = uid_count
                break
            elif(top3List[i][1] < count):
                for j in range(2,i,-1):
                    top3List[j] = top3List[j-1]
                top3List[i] = uid_count
                break
    return site,top3List
def getTopsUid2(one):
    # one=(site,[(uid,count),(uid,count),...])
    site = one[0]
    uid_count_iterable = one[1]
    newlist = sorted(uid_count_iterable,key=lambda one:one[1],reverse=True)
    returnlist = []
    if len(newlist)>3:
        returnlist = newlist[:3]
        # for i in range(0,3):
        #     returnlist.append(newlist[i])
    else:
        returnlist = newlist
    return site,returnlist


if __name__ == '__main__':
    conf = SparkConf().setMaster("local").setAppName("wc")
    sc = SparkContext(conf=conf)

    file_rdd = sc.textFile("./pvuv.txt")
    #每个网站的pv
    pv_rdd = file_rdd.map(lambda s:(s.split("\t")[4],1)).reduceByKey(lambda v1,v2:v1+v2)
    #pv_rdd.foreach(print)

    #每个网站的uv
    uv_rdd = file_rdd.map(lambda s:(s.split("\t")[4],s.split("\t")[2])).distinct()\
        .map(lambda s:(s[0],1)).reduceByKey(lambda v1,v2:v1+v2)
    uv2_rdd = file_rdd.map(lambda line:(line.split("\t")[4]+"_"+line.split("\t")[2])) \
        .distinct().map(lambda s:(s.split("_")[0],1)).reduceByKey(lambda v1,v2:v1+v2)
    #uv_rdd.foreach(print)

    #统计每个网站的最活跃的top3地区
    active_top2_rdd = file_rdd.map(lambda s:(s.split("\t")[4]+"_"+s.split("\t")[3],1))\
        .reduceByKey(lambda v1,v2:v1+v2).sortBy(lambda s:s[1],ascending=False).map(lambda s:(s[0].split("_")[0],s[1])).take(2)
    active_top2_rdd = file_rdd.map(lambda s:(s.split("\t")[4],s.split("\t")[3])) \
        .reduceByKey(lambda v1,v2:v1+v2).sortBy(lambda s:s[1],ascending=False).map(lambda s:(s[0].split("_")[0],s[1])).take(2)
    active_top2_rdd = file_rdd.map(lambda s:(s.split("\t")[4],s.split("\t")[3])) \
        .reduceByKey(lambda v1,v2:v1+v2).sortBy(lambda s:s[1],ascending=False)
    file_rdd.map(lambda s:(s.split("\t")[4],s.split("\t")[3]))\
        .groupByKey().map(lambda one:get_top3_local(one)).foreach(print)
    print("#####################################")
    #print(active_top2_rdd)

    #统计每个网站最热门的操作

    #统计每个网站下最活跃的top3用户
    file_rdd.map(lambda s:(s.split("\t")[4],s.split("\t")[2])) \
        .groupByKey().map(lambda one:get_top3_local(one)).foreach(print)
    print("#####################################")
    file_rdd.map(lambda s:(s.split("\t")[2],s.split("\t")[4]))\
        .groupByKey().flatMap(lambda s:uid_sites_site_uid_shu(s))\
        .groupByKey().map(lambda one:getTopsUid(one)).foreach(print)
    print("#####################################")
    file_rdd.map(lambda s:(s.split("\t")[2],s.split("\t")[4]))\
        .groupByKey().flatMap(lambda s:uid_sites_site_uid_shu(s))\
        .groupByKey().map(lambda one:getTopsUid2(one)).foreach(print)






    #统计每个网站最热门的操作
    oper_top_rdd = file_rdd.map(lambda s:(s.split("\t")[4]+"_"+s.split("\t")[5],1)) \
        .reduceByKey(lambda v1,v2:v1+v2).sortBy(lambda s:s[1],ascending=False).map(lambda s:(s[0].split("_")[0],s[1])).first()
    #print(oper_top_rdd)

    #统计每个网站下最活跃的top3用户
    user_top3_rdd = file_rdd.map(lambda s:(s.split("\t")[4]+"_"+s.split("\t")[2],1)) \
        .reduceByKey(lambda v1,v2:v1+v2).sortBy(lambda s:s[1],ascending=False).take(3)#.map(lambda s:(s[0].split("_")[0],s[1])).take(5)
    #print(user_top3_rdd)
