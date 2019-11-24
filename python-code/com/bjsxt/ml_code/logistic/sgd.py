import numpy as np
import matplotlib.pyplot as plt


def h(x):
    return w0 + w1*x[0]+w2*x[1]


if __name__ == '__main__':
    #  y=3 + 2 * (x1) + (x2)  #自己设计一个线性回归的公式 W0=3 W1=2,W2=1
    rate = 0.001 # 步长，系数
    x_train = np.array([[1, 2], [2, 1], [2, 3], [3, 5], [1, 3], [4, 2], [7, 3], [4, 5], [11, 3], [8, 7]])
    y_train = np.array([7, 8, 10, 14, 8, 13, 20, 16, 28, 26])
    x_test = np.array([[1, 4], [2, 2], [2, 5], [5, 3], [1, 5], [4, 1]])

    w0 = np.random.normal()
    w1 = np.random.normal()
    w2 = np.random.normal()
    print("随机W0:%s,随机的W1:%s,随机的W2:%s"%(w0,w1,w2))
    for i in range(4000): #迭代8000
        for x, y in zip(x_train, y_train):
            w0 = w0 - rate*(h(x)-y)*1
            w1 = w1 - rate *(h(x)-y)*x[0]
            w2 = w2 - rate*(h(x)-y)*x[1]
        plt.plot([h(xi) for xi in x_test])
        print("w0导数 = %f ,w1导数 = %f ,w2导数 = %f "%(rate*(h(x)-y)*1,rate*(h(x)-y)*x[0],rate*(h(x)-y)*x[1]))

    print(w0)
    print(w1)
    print(w2)

    result = [h(xi) for xi in x_train]
    print(result)

    result = [h(xi) for xi in x_test]
    #[9,9,12,16,10,12]
    print(result)


    plt.show()
