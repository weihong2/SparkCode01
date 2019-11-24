# a = int(input("请输入："))
# if a<4:
#     print("测试",end="\t")
#     print("a<4, a value is %d"%a)
# else:
#     pass
#     print("a>4, valur is %d"%a)
# for x  in [1,2,3]:
#     print(x,end=" ")
# _name_ = "_main_"
#type(range[1,2])
# for x in range[1,10]:
#     print(x,end=" ")

# for  i in range(1,10):
#     for  j in range(1,i+1):
#         print("%d*%d=%s"%(j,i,j*i),end="\t")
#     print()

print("hello,world".replace("o,w","w,o"))

print(__name__)
#print(__dict__)
print(__doc__)
print(__file__)
print("hello,world"[::])
print("hello,world"[::-2].center(2))
print(__file__[::-2].center(len(__file__)))
print(__file__.split("/"))
print(__file__.partition("/"))
print()

print("key=%s, value1=%s , value2=%s"%(("hello","world","python")))
d={(3+4j): 3.44, 123.455: 'hello'}
# items()以列表返回可遍历的(键, 值) 元组数组
for i in d.items():
    print("key=%s, value=%s"%i) # i 是二元组

print(__package__)

a,b=0,1
c=int(input())
while b<c:
    print(b,end=" ")
    a,b = b,a+b

# for x in 'abcds':
#     print(x)

#print(__path__)

# for i in range(1,10):
#     for j in range(1,i+1):
#         print("%d*%d="%(j,i,j*i),end="\t")
#     print()


# if _name_ == "_main_":
#     age=int(input("输入年龄："))
#     if age<18:
#         print("未成年")
#     elif age>=18 and age<30:
#         print("青年")
#     else:
#         print("中老年")
