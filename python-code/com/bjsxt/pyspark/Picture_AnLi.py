#案例：把一个目录下的所有图片，全部转成矩阵，读写图片的模块，PIL
import numpy as np
import os
from PIL import Image
import pickle

def image_to_array(dir):
    big_array = []
    for file in os.listdir(dir):
        image = Image.open(os.path.join(dir,file))
        #取得图片中每个像素点的RGB数组
        r,g,b = image.split()
        r_array = np.array(r).reshape(62500) #转为一维数组
        g_array = np.array(g).reshape(62500)
        b_array = np.array(b).reshape(62500)
        image_array = np.concatenate((r_array,g_array,b_array)) #(3*62500，)
        print("image_array: ",image_array.shape)
        #一维数组水平合并，一张图片 = arr.shape(3*62500,)
        big_array = np.concatenate((image_array,big_array))
        print("big_array---: ",big_array.shape)
    # 一个文件 = arr.shape( 图片数*3*图片像素*图片像素 , )
    big_array = big_array.reshape((len(os.listdir(dir)),3*62500))
    print("big_array-finally: ",big_array.shape)
    f = open("./images.bin","wb")
    pickle._dump(big_array,f)
    f.close()


def read_array_to_images(b_file):
    f = open(b_file,"rb")
    big_array = pickle.load(f)
    images_count = big_array.shape[0]
    big_array = big_array.reshape((images_count,3,250,250))
    for i in range(0,images_count):
        print(big_array[i][0].shape)
        r = Image.fromarray(big_array[i][0]).convert("L")
        g = Image.fromarray(big_array[i][1]).convert("L")
        b = Image.fromarray(big_array[i][2]).convert("L")
        image = Image.merge("RGB",(r,g,b))
        #image.show()
        image.save("./result/%s.jpg"%i,"jpeg") #(file_path, 格式)，格式：png，jpg...


def image_to_array2(dir):
    big_array = []
    for file in os.listdir(dir):
        image = Image.open(os.path.join(dir,file))
        r,g,b = image.split()
        r_array = np.array(r).reshape(62500)
        g_array = np.array(g).reshape(62500)
        b_array = np.array(b).reshape(62500)
        image_array = np.concatenate(r_array,g_array,b_array) #(3*62500,)
        big_array = np.concatenate((big_array,image_array))
    big_array = big_array.reshape((len(os.listdir(dir)),3*62500))
    f = open("./images.bin","wb")
    pickle._dump(big_array,f)
    f.close()


def read_array_to_images2(b_file):
    f = open(b_file,"rb")
    big_array = pickle.load(f) # (图片数, 3*62500)
    image_nums = big_array.shape[0]
    for i in range(image_nums):
        image_array = big_array[i].reshape((3,250,250))
        print(len(image_array[0]))
        r = Image.fromarray(image_array[0]).convert("L")
        g = Image.fromarray(image_array[1]).convert("L")
        b = Image.fromarray(image_array[2]).convert("L")
        image = Image.merge("RGB",(r,g,b))
        #image.show()



if __name__ == '__main__':
    dir = "./images"
    #image_to_array(dir)
    #image_to_array2(dir)
    b_file = "./images.bin"
    #read_array_to_images(b_file)
    read_array_to_images2(b_file)
    a = np.array([[1,2,3],[4,5,5]])
    print(a.shape)
    print(a)
    print(len(a))
    print(a[0])
