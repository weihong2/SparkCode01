import numpy as np
from sklearn.linear_model import LogisticRegression
from sklearn.feature_extraction.text import TfidfVectorizer

from sklearn.model_selection import train_test_split
def  spam_email_logistic():
    f =open("../sms_spam.txt",encoding="utf-8")
    lines =f.readlines() #读取整个文件内容
    #从这些数据中（随机）抽取一部分为训练集，另外一部分为测试集
    email_dataset=np.array(list(map(lambda x:x.split(',',1),lines)))[1:,]
    y_dataset= email_dataset[:,0] =='spam' #True代表垃圾邮件，false代表正常邮件
    x_dataset =email_dataset[:,1]

    train_x,test_x,train_y,test_y=train_test_split(x_dataset,y_dataset) #得到所有的测试集和训练集的数据

    vectorizer=TfidfVectorizer() #向量化的工具类，把邮件内容中的每个词拿出来，并且计算每个词的权重。
    train_x=vectorizer.fit_transform(train_x) #把训练集的x向量化
    model =LogisticRegression()
    model.fit(train_x,train_y) #根据训练集来训练模型

   #把测试集数据向量化
    test_x=vectorizer.transform(test_x)
    result =model.predict(test_x) #result是邮件类型，

    acc_list=(result==test_y)
    correct=sum(acc_list) / len(acc_list)
    print("准确率是:",correct)

    print(acc_list)

    # email1="Congratulation! You have ben selected to a big prize of $1000！ Call 911-800 to know hwo to get it!"
    email2="hi darling ,what do you want to eat tonight?"
    vector_email=vectorizer.transform([email2])
    print(model.predict(vector_email)) #true，false

if __name__ == '__main__':
    spam_email_logistic()