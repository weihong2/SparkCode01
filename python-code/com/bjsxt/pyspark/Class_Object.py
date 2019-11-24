"""
类
    方法类别            语法              描述
    成员方法(对象方法)  def 方法名       第一个形参self，默认传递
    类方法             @classmethod     第一个形参cls，默认传递
    静态方法           @staticmethod    没有默认传递的形参

    属性类别       定义            私有             描述
    成员属性     self.属性名    self.__属性名    初始化函数中定义
    类属性       属性名           __.属性名      类中定义，被所有实例对象共享，内存只有一个副本

类外修改类属性
    1.类对象去引用进行修改
    2.通过实例对象去引用，会产生一个同名的实例属性，修改不会影响到类属性
        且之后通过实例对象去引用该名称的属性，实例属性会强制屏蔽类属性，即引用的是实例属性
        除非删除了该实例属性：del p.country


多继承
     调用父类中相同函数，且自身没有定义，调用第一个父类中函数
"""
class A:
    def test(self):
        print("A -- test()")
class B:
    def test(self):
        print("B -- test()")
class C(A,B): # 多继承
    pass


class Person:
    #self相当于java中的this，代表当前对象
    #cls：类函数的第一个参数，代表当前的类
    #self，cls可以改任意名字

    city = "beijing" # 类属性，静态属性-->访问通过：类名.属性  对象.属性
    __country = "china" # 私有的类属性

    # 类函数：1.classmethod装饰
    #        2.self --> cls
    @classmethod # 装饰
    def test2(cls):
        print("test2 is 类函数")

    # 静态函数
    @staticmethod
    def test3():
        print("test3 is 静态函数")

    # 对象的初始化函数，不是构造函数
    def __init__(self,xname,xage,xsex):
        self.name = xname #成员属性
        self.age = xage
        self.__sex = xsex #私有的成员属性

    def sayHello(self): #成员函数
        print("class object")
        print("name = %s , age = %s , sex = %s"%(self.name,self.age,self.__sex))

#设计模式--单例
class Single_Class:
    __instance = None
    __init_flag = False

    def __init__(self):
        if not self.__init_flag:
            print("初始化函数")
            self.__init_flag = True

    def __new__(cls, *args, **kwargs):
        print("构造函数")
        if not cls.__instance:
            cls.__instance = object.__new__(cls)
        return cls.__instance

if __name__ == '__main__':
    p = Person("zs",23,"female")
    p.sayHello()
    Person.city = "shanghai"
    print(Person.city)
    Person.test2()
    Person.test3()
    c = C()
    c.test()

    m = Single_Class() # 步骤：1.构造函数 2.初始化函数
    n = Single_Class()
    print(n==m)
