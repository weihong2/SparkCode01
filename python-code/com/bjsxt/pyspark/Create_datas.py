import random
import time

#每一行数据包括：时间，客户端IP，用户ID，地域信息city，访问的网址，用户操作
def create_datas(datas_file):
    for i in range(1000):
        date = "%s %s:%s:%s"%(time.strftime("%Y-%m-%d"),random.randint(1,24),random.randint(0,59),random.randint(0,59))
        ips = [str(random.randint(0,255)),str(random.randint(0,255)),str(random.randint(0,255)),str(random.randint(0,255))]
        ip = ".".join(ips)
        uid = get_user_id("u_")
        city_list = ["changsha","beijing","shanghai","hangzhou","nanjing"]
        city = city_list[random.randint(0,4)]
        site_list = ["wwww.baidu.com","www.taobao.com","www.csdn.com","www.jd.com"]
        operate_list = ["login","logout","click","order","collections","register"]
        for j in range(random.randint(0,9)):
            site = site_list[random.randint(0,3)]
            operate = operate_list[random.randint(0,5)]
            line = date+"\t"+ip+'\t'+uid+'\t'+city+'\t'+site+'\t'+operate
            with open(datas_file,"a",encoding="UTF-8") as file:#相对于普通open，这种自动关闭file对象
                file.writelines([line,"\n"])


#随机用户ID：u_数字(4为数字)
def get_user_id(prefix):
    id = str(random.randint(0,9999)) #但可能出现不够4位的数字
    for i in range(4-len(id)):
        prefix += "0" #不够4位,补0
    return prefix+id

if __name__ == '__main__':
    datas_file="./pvuv.txt"
    create_datas(datas_file)
    date = "%s %s:%s:%s"%(time.strftime("%Y-%m-%d"),random.randint(1,24),random.randint(0,59),random.randint(0,59))
    print(date)