package com.bjsxt.streaming

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver

/**
  * 接收器
  * @param host
  * @param port
  */
class MyReceiver(host:String,port:Int)extends Receiver[String](StorageLevel.MEMORY_ONLY){
  //接收器启动回调的方法
  override def onStart(): Unit = {
    //启动线程，一直运行
    new Thread(new Runnable {
      override def run(): Unit = {
        //以批次接收数据
        receiver()
      }
    },"myReceiver").start()
  }
  def receiver()={
    var socket:Socket = null
    var reader: BufferedReader = null
    try {
      socket = new Socket(host, port)
      reader= new BufferedReader(new InputStreamReader(socket.getInputStream))
      var line: String = reader.readLine()
      while (line!=null){
        store(line)
        line = reader.readLine()
      }
    } catch {
      case e:Exception =>e.printStackTrace()
        reader.close()
        socket.close()
    }
    /*try(val reader2 = new BufferedReader(new InputStreamReader(new Socket(host,port).getInputStream))) {
      reader2.re
    }*/
  }

  //停止
  override def onStop(): Unit = ???
}
