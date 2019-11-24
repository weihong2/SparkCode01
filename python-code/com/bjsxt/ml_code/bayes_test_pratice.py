import codecs

from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB

if __name__ == '__main__':
    f = codecs.open("sms_spam.txt","rb")
    labeles = []
    contentes = []
    line = f.readline().decode("utf-8")
    while True:
        line = f.readline().decode("utf-8")
        if line:
            arr_line = line.split(",")
            if arr_line[0]=="ham":
                labeles.append(1)
            else:
                labeles.append(0)
            contentes.append(arr_line[1])
        else:
            break

    # 向量化
    vectorizer1 = CountVectorizer()
    train_datas = vectorizer1.fit_transform(contentes[:5548])
    print(train_datas.toarray())

    # 训练模型
    model = MultinomialNB(alpha=1.0)
    model.fit(train_datas, labeles[:5548])

    # 使用模型来预测分类，注意：传入测试集,测试集向量化的时候，词表和训练集的词表一样
    vectorizer2 = CountVectorizer(vocabulary=vectorizer1.vocabulary_)
    test_datas = vectorizer2.fit_transform(contentes[5548:])
    predict_result = model.predict(test_datas)

    for i in predict_result:
        if i==0:
            print("垃圾邮件")
        else:
            print("不是垃圾邮件")


# if __name__ == '__main__':
#     #读取数据sms_spam.txt
#     f = codecs.open("sms_spam.txt","rb")
#     labeles = []
#     contentes = []
#     count = 0
#     while True:
#         line = f.readline().decode("utf-8")
#         if count==0:
#             count +=1
#             continue
#         if line:
#             array_line = line.split(",")
#             if array_line[0]=="ham":
#                 labeles.append(1)
#             else:
#                 labeles.append(0)
#             contentes.append(array_line[1])
#         else:
#             break
#
#     vectorizer1 = CountVectorizer()
#     # train_datas = vectorizer1.fit_transform(contentes[:5548])
#     train_datas=vectorizer1.fit_transform(contentes[:5548])
#     print(train_datas.toarray())
#
#     model = MultinomialNB()
#     model.fit(train_datas,labeles[:5548]) # x,y
#
#     vectorizer2 = CountVectorizer(vocabulary=vectorizer1.vocabulary_)
#     test_datas=vectorizer2.fit_transform(contentes[5548:])
#     print(test_datas.toarray())
#     predict_result = model.predict(test_datas)
#
#     for i in predict_result:
#         if i==0:
#             print("垃圾邮件")
#         else:
#             print("不是垃圾邮件")