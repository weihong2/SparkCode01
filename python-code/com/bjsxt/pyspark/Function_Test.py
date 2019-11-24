
def test1(a,*b):#不定长参数
    print(a)
    print(b)#元组

def test2(a,**b):#包含有键值对的不定长参数
    print(a)
    print(b)#字典
    pass

def test3(a,b=100):
    print(b)

def test4(a,list1=[10,20]):
    list1.append(a)
    return list1

a=200 #全局变量
b = [1,2,3]
def test5():
    # 在函数中默认不能修改全局变量的引用，需要global关键字
    #a =100 #相当于在当前作用域中新建变量
    global a
    a=111
    b.append(44.44)
    print(a)
    print(b)
test5()
print(a)
print(b)

print("_ _ _ _  _ _ __ __ _ _")
if __name__=='__main__':
    test2(100,a2=200,b=300,c=4000)
    test1(1,2,3,4,5)
    test3(11,22)
    test3(11)
    print(test4(123))
    list2=[456,788]
    print(test4(123,list2))
    print(test4(321))
    print(test4(555,list2))

func1 = lambda v1,v2:v1+v2
print(func1(22,33.33))

print("************************************")
#高阶函数
list2=[1,2,3]
r = map(lambda x:x**2,list2)
print(r)
print(list(r))

def h_test1(x,y,func1):
    return func1(x,y)
print(h_test1(100,200,lambda x,y:x+y))

#python标准库，常用的高阶函数：map，filter，sorted，"""reduce
r1 = filter(lambda x:x%2==1,list2)
#filter(function,iterable)
#map(func,iter)
#sorted()
print(r1)
print(list(r1))

#sorted(),根据元素的绝对值排序
list3=[9,3,-20,-8,100,67,32,-5]
print(sorted(list3,key=abs))
#print(sorted(list3,key=abs),reverse=True)






#--------------------------------------
print("$$$$$$$$$$$$$$$$$$$")
def fun(a,b,*args,**kvargs):
    print("a=%s, b=%s"%(a,b))
    print("args = ",args)
    print("kvargs = ")
    for key,value in kvargs.items():
        print("key = ",key," , value = ",value)
fun(1,2,3,4,5,m=6,n=7,k=555)
