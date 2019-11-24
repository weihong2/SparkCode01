package com.bjsxt.streaming

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver

class MyReceiver2(host:String,port:Int)extends Receiver[String](StorageLevel.MEMORY_AND_DISK) {
  override def onStart(): Unit = {
    new Thread(new Runnable {
      override def run(): Unit = {
        var socket:Socket = null
        var reader:BufferedReader = null
        try{
          socket = new Socket(host,port)
          reader = new BufferedReader(new InputStreamReader(socket.getInputStream))
          var line: String = reader.readLine()
          while (line != null){
            store(line)
            line = reader.readLine()
          }
        }catch{
          case e:Exception=>e.printStackTrace()
            reader.close()
            socket.close()
        }
      }
    })
  }

  override def onStop(): Unit = ???
}
