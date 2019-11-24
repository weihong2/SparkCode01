import numpy as np
"""
数组的初始化
    1- np.array(p_object, dtype=None, copy=True, order='K', subok=False, ndmin=0)
    2- np.arange(start, stop, step, dtype)
    3- np.random.randint(low, high=None, size=None, dtype='l')
    4- zeros(shape, dtype=None, order='C')
       ones(shape, dtype=None, order='C')
重新构建数组的维度，保证size不变前提下
    np.reshape(self, shape, *shapes, order='C')

切片
    arr[start : stop : step]
    arr[行 ， 列]
    
计算：
    + - 保证：列数相同
        结果：shape为矩阵中行数最大的
    * 保证：A矩阵的列数 = B矩阵的函数  
      结果：shape( A矩阵的行数，B矩阵的列数 )  
    >,< 例：arr>4，arr[arr>4]
    特殊函数：num([0/1]),1-行，0-列
            np.linalg.inv(arr)，求逆矩阵
    
索引赋值
    a[[1]]=2
    a[2,4]=555，矩阵下标

遍历
    1.双for循环
    2.使用flat函数,单for循环
    3.flatten()函数：转为一维数组
    
形状操作：v垂直，h水平
    stack,concatenate合并，stack不能合并一个空矩阵
    concatenate能合并空矩阵，还能合并多个
    concatenate,默认垂直，但合并的是一维数组，水平
    
复制
    np.tile(arr,倍数)，每一行复制几倍

切割
    np.hsplit(arr33,份数)，等量切割
    np.array_split(np.arange(10),份数)，随机切割
    
"""
if __name__ == '__main__':
    list1 = [[1,2,3,4],[7,8,9,10]]
    # np.array(p_object, dtype=None, copy=True, order='K', subok=False, ndmin=0)
    arr1 = np.array(list1)
    print(arr1)
    print("矩阵的秩：%s"%arr1.ndim)
    print("矩阵的维度：",arr1.shape)
    print("矩阵的类型：%s"%arr1.dtype)

    #第二种，初始化一些值为0、1的数组
    print(np.zeros((2,5)))
    print(np.ones((3,4)))
    #np.empty()

    #第三种，随机生成数组，或者根据范围生成矩阵中的各个元素
    # np.arange(start, stop, step, dtype)
    # np.reshape(self, shape, *shapes, order='C')
    arr2 = np.arange(27).reshape((3,3,3))
    print(arr2)
    print("矩阵的秩：%s"%arr2.ndim)
    print("矩阵的维度：",arr2.shape)
    print("矩阵的类型：%s"%arr2.dtype)
    # np.random.randint(low, high=None, size=None, dtype='l')
    print(np.random.randint(0,10,16).reshape((2,2,2,2)))

    #--------切片-------------
    arr3 = np.arange(1,13).reshape((3,4))
    print(arr3)
    print("-=-=-=-=")
    print(arr3[::2])

    arr4 = np.arange(1,21).reshape((5,4))
    print(arr4)
    print("-=-=-=-=---------")
    print(arr4[[0,2]]) # 取第一行 和 第三行
    print("-=-=-=-=---------")
    print(arr4[:,-2:]) # 取最后两列
    print("-=-=-=-=---------")
    print(arr4[:,[1,3]]) # 取 第二列 和 第四列
    print("-=-=-=-=---------")
    print(arr4[[0,2],[1,3]]) # 取(分别)第一、三行 的 第2，4列
    print("-=-=-=-=---------")
    print(arr4[[0,2]][:,[1,3]]) # 取 第一、三行 的 第2，4列
    print("-=-=-=-=---------")

    # 赋值
    print("-----赋值------")
    print(arr4)
    arr4[[0]] = 2 # 第一行赋值为1
    arr4[4,3] = 555 #
    print(arr4)
    print("--------------====")

    # 计算
    arr5 = np.array(([1,2,3],[4,5,6],[1,2,2]))
    arr6 = np.array([[6,7,9]])
    arr7 = np.array([6,7,9])
    print(arr6)
    print(arr6.shape)
    print(arr7)
    print(arr7.shape)
    print(arr6-arr5)
    print("_-_-_-乘法_-_-_-")
    print(arr6.dot(arr5)) # 乘法
    print(np.dot(arr6,arr5))
    print("_-_-_-_-_-_-")
    print(arr5>4)
    print(arr5[arr5>4])
    print(arr5[arr5>4].sum())
    print("_-_-_-_-_-_-")
    print(arr5)
    print(arr5.sum(1)) #所有行相加
    #print(arr5.sum(axis=1))
    print(arr5.sum(0)) #所有列相加

    print("_-_-_-_-_-_-")
    arr8 = np.array([[1,2],[3,4]])
    print(np.linalg.inv(arr8))
    print("_-_-_-_-_-_-")
    arr9 = np.array([[2,3],[4,5]])
    print(np.linalg.inv(arr9))

    # 遍历
    #print(arr8[0,0])
    #print(arr8[1])
    print("遍历")
    for i in arr8:
        #print(i)
        for j in i:
            print(j)
    print(arr8.flat)
    for i in arr8.flat:
        print(i)
    print(arr8.flatten()) # 合成一维数组

    # 形状操作：v垂直，h水平
        # stack,concatenate合并，stack不能合并一个空矩阵
        # concatenate能合并空矩阵，还能合并多个
    print("_-_-_-_-_-_-")
    arr11 = np.array([[1,2,3],[1,2,3]]) #(2,3)
    arr12 = np.array([[4,5,6],[7,8,9]]) #(2,3)
    print(np.stack((arr11,arr12)),np.stack((arr11,arr12)).shape) #(2, 2, 3)
    print(np.vstack((arr11,arr12)),np.vstack((arr11,arr12)).shape) #(4, 3) 垂直
    print(np.hstack((arr11,arr12)),np.hstack((arr11,arr12)).shape) #(2, 6) 水平
    print("_-_-_-_-_-_-")
    print(np.concatenate((arr11,arr12))) #(4, 3)
    print(np.concatenate((arr11,arr12),axis=0)) #(4, 3)
    print(np.concatenate((arr11,arr12),axis=1))

    #复制
    print("_-_-_复制-_-_-_-")
    arr22 = np.random.randint(0,10,12).reshape((3,4))
    print(arr22)
    print(np.tile(arr22,2))
    print(np.tile(arr22,3))

    #切割
    print("_-_-_-_-_-_-")
    arr33 = np.arange(12).reshape((2,6))
    print(arr33)
    print(np.hsplit(arr33,2)) # 分成两份
    print(np.hsplit(arr33,3)) # 分成3份
    print(np.array_split(np.arange(10),3)) #不等量切割



