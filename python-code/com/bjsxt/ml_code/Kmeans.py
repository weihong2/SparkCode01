import numpy as np#将每行数据放入一个数组内列表，返回一个二维列表
def loadDataSet(fileName):  #建空列表
    dataMat = []
    fr = open(fileName)
    for line in fr.readlines():
        #按照制表符切割每行，返回一个列表list
        curLine = line.strip().split('\t')#切分后的每个列表中的元素，以float形式返回，map()内置函数，返回一个list
        fltLine = map(float,curLine)
        dataMat.append(fltLine)
    return dataMat
#离
def distEclud(vecA, vecB):
    return np.sqrt(np.sum(np.power(vecA - vecB, 2)))
#3个中心点的位置坐标，返回一个3*2的矩阵
def randCent(dataSet, k):#列数,2列
    n = np.shape(dataSet)[1]
    '''
        centroids是一个3*2的矩阵，用于存储三个中心点的坐标
    '''
    centroids = np.mat(np.zeros((k,n)))
    for j in range(n):
        #统计每一列的最小值
        minJ = min(dataSet[:,j])
        #每列最大值与最小值的差值
        rangeJ = float(max(dataSet[:,j]) - minJ)
        #random.rand(k,1) 产生k*1的数组，里面的数据是0~1的浮点型。
        array2 = minJ + rangeJ * np.random.rand(k,1)
        #转换成k*1矩阵 赋值给centroids
        centroids[:,j] = np.mat(array2)
    return centroids

def kMeans(dataSet, k, distMeas=distEclud, createCent=randCent):
    #计算矩阵所有行数  80
    m = np.shape(dataSet)[0]
    #y.mat 将二维数组转换成矩阵
    clusterAssment = np.mat(np.zeros((m,2)))
    #createCent找到K个随机中心点坐标
    centroids = createCent(dataSet, k)
    #     print centroids
    clusterChanged = True
    while clusterChanged:
        clusterChanged = False
        #遍历80个数据到每个中心点的距离
        for i in range(m):
            #np.inf float的最大值，无穷大
            minDist = np.inf
            #当前点属于的类别号
            minIndex = -1
            #每个样本点到三个中心点的距离
            for j in range(k):
                #返回两点距离的值
                distJI = distMeas(centroids[j,:],dataSet[i,:])
                if distJI < minDist:
                    #当前最小距离的值
                    minDist = distJI
                    #当前最小值属于哪个聚类
                    minIndex = j
            #有与上次迭代计算的当前点的类别不相同的点
            if clusterAssment[i,0] != minIndex:
                clusterChanged = True
                #将当前点的类别号和最小距离 赋值给clusterAssment的一行
            clusterAssment[i,:] = minIndex,minDist
        for cent in range(k):
            #ent[:,0].A==censInClust #取出的是对应是当前遍历cent类别的 所有行数据组成的一个矩阵
            ptsInClust = dataSet[np.nonzero(clusterAssment[:,0].A==cent)[0]]#心点坐标的位置
            centroids[cent,:] = np.mean(ptsInClust, axis=0)
    #返回 【 当前三个中心点的坐标】 【每个点的类别号，和到当前中心点的最小距离】
    return centroids, clusterAssment

if __name__ == '__main__': #80*2的矩阵
    dataMat = np.mat(loadDataSet('./testSet.txt'))
    k=3
    centroids, clusterAssment =\
        kMeans(dataMat, k, distMeas=distEclud, createCent=randCent)
    print(centroids)
    print(clusterAssment)
