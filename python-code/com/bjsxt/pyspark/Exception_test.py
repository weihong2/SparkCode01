"""
导入
    import os #导入整个模式
        使用：os.listdir() -->模块名.方法
        别名：import os as myos

    from os import path #导入模块中的一部分，细腻化导入
        使用：path.方法
"""
#from pyspark import SparkConf

#from .Function_Test import *
#from com.bjsxt.pyspark.Function_Test import *
# 自动执行了不是main中的函数
# print("")
# test1(23,3,3,3)

# def func(b,*a):
#     return b,a
# a,b=func(1,2,3)
# print(a,b,end="\t")
# print()

class Animal(object):
    # 初始化方法
    # 创建完对象后会自动被调用
    def __init__(self, name):
        print('__init__方法被调用')
        self.__name = name
    # 析构方法
    # 当对象被删除时，会自动被调用
    def __del__(self):
        print("__del__方法被调用")
        print("%s对象马上被干掉了..."%self.__name)
# 创建对象
dog = Animal("哈皮狗")
# 删除对象
del dog
cat = Animal("波斯猫")
cat2 = cat
cat3 = cat
print("---马上 删除cat对象")
del cat
print("---马上 删除cat2对象")
del cat2
print("---马上 删除cat3对象")
del cat3
print("程序2秒钟后结束")


# try:
#     1/0
# except ZeroDivisionError as e:
#     print(e)
#
# try:
#     1/0
# #except: # 捕获所有异常
# except Exception as e: # 捕获所有异常
#     print(e)
#     #raise  # 抛出异常


class AA:
    def __init__(self):
        print('A -- init()')
    def __new__(cls, *args, **kwargs):
        print("A new ---")
        return object.__new__(cls)
    # def __del__(self):
    #     print("A -- def")
    pass
class BB(AA):
    def __init__(self):
        print("B -- init()")
    def __new__(cls, *args, **kwargs):
        print("B new ---")
        return object.__new__(cls)
    def __str__(self):
        return "B toString --str"
    # def __del__(self):
    #     print("B del")
print("++++++___+++++++++++_______+")
bb = BB()
print(bb)
del bb
