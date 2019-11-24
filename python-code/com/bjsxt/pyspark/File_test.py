"""
open("目录","方式：r/w/...") #目录：.是以当前文件的
"""
if __name__ == '__main__':
    #f=open("./data1","r")
    f=open("E:\\test\\test.txt","r")
    # line=f.readline()
    # while line!="":
    #     print(line)
    #     line=f.readline()
    # f.close()

    for line in f.readlines():
        print(line)

print("aaa/bbbb/cccc".rsplit("/"))

"""
文件操作：import os
    os.path.isdir(目录)
    os.path.join(目录，文件名) #转为路径
    os.path.split(file) #返回文件路径和文件名
"""
#把目录中所有后缀为“txt”的文件改为：change_源文件名.js
import os
def change_file_name(file):
    if os.path.isdir(file):
        file_array = os.listdir(file) #文件名数组
        for f in file_array:
            change_file_name(os.path.join(file,f))
    else:
        #file_name = os.path.split(file)[1] #文件名称
        file_name = os.path.basename(file) #文件名称
        if file_name.endswith(".txt"):
            # new_file_name = "change_"+file_name[:-4]+".js"
            new_file_name = "change_"+file_name[:file_name.rfind(".txt")]+".js"
            new_file_path = os.path.split(file)[0]+"/"+new_file_name
            os.rename(file,new_file_path)

#change_file_name("E:\\test")

