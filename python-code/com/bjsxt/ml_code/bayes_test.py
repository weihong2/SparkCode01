import codecs

from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB

if __name__ == '__main__':
    #读取数据sms_spam.txt

    f=codecs.open("./sms_spam.txt",'rb')
    labeles=[]
    contents=[]
    count=0
    while True:
        line=f.readline().decode("utf-8")
        if count==0:
            count+=1
            continue
        if line:
            array_line=line.split(",")
            if (array_line[0]=="ham"):  #把垃圾邮件用 0代表，正常邮件用1 代表。
                labeles.append(1)
            else:
                labeles.append(0)
            contents.append(array_line[1])
        else:
            break

    #从数据中分离得到测试集和训练集。使用最后10行数据为测试集，其他都为训练集
    # 把训练集邮件的内容向量化。

    vectorizer1 = CountVectorizer() #把训练集向量化的工具，把文本向量化，统计次数
    train_datas=vectorizer1.fit_transform(contents[:5548])  #后10行为测试集，前面的都为训练集

    print("vectorizer1.get_feature_names is ",vectorizer1.get_feature_names())
    print("fea_train is ", train_datas.toarray())

    #开始训练模型，由于有多个特征，所有要设置拉普拉斯估计
    bayes=MultinomialNB(alpha=1.0)
    bayes.fit(train_datas,labeles[:5548])

    #使用模型来预测来分类 ,注意：传入测试集,测试集向量化的时候，词表和训练集的词表一样
    vectorizer2 =CountVectorizer(vocabulary=vectorizer1.vocabulary_) #vocabulary词库
    test_datas=vectorizer2.fit_transform(contents[5548:])
    predict_result=bayes.predict(test_datas)  #结果中有10行数据,0和1

    #验证预测的结果

    for pr in predict_result:
        if pr==0:
            print("垃圾邮件")
        else:
            print("正常邮件")



