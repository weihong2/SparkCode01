from com.bjsxt.ml_code import KNNDateOnHand
from sklearn.neighbors import NearestNeighbors

if __name__ == '__main__':
    datas_matrix,label_array=KNNDateOnHand.file2matrix("./datingTestSet.txt")
    norm_martrix,ranges,minVals=KNNDateOnHand.autoNorm(datas_matrix)
    model=NearestNeighbors(n_neighbors=4).fit(norm_martrix)

    test_data = [20000, 10, 2.8]
    #把这一条测试集数据归一化
    norm_test_data=(test_data-minVals)/ranges
    #根据测试数据，计算出距离，并且排序，之后取前4条数据的下标位
    dists,indexs=model.kneighbors([norm_test_data])

    print("距离数组%s"%dists)
    print("下标位数组%s"%indexs)

    #根据下标位拿到对应的分类,把分类放到字典中：Key：分类，Value:个数
    classes_dict={}
    for i in range(4):
        class_value=label_array[indexs[0][i]]
        classes_dict[class_value]=classes_dict.get(class_value,0)+1

    sort_result=sorted(classes_dict.items(),key=lambda x:x[1],reverse=True)
    resultList = ['不喜欢', '一般', '有好感']
    print(resultList[sort_result[0][0]-1])