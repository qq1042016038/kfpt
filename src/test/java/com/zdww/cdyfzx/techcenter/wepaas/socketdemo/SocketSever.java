package com.zdww.cdyfzx.techcenter.wepaas.socketdemo;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketSever {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket  =new ServerSocket(9999);
        //等待客户端的连接
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream =new DataInputStream(inputStream);
        while (true){
            byte[] top = new byte[1];
            dataInputStream.read(top);
            String topStr = new String(top);
            if(!(topStr.equals("{"))){
                continue;
            }
            byte[] heads = new byte[4];
            dataInputStream.read(heads);
            String head = new String(heads);
            System.out.println(head);
            String dataLength = head.substring(head.indexOf(',')+1, head.length() - 1);
            System.out.println(dataLength);
            byte[] data = new byte[Integer.parseInt(dataLength)];
            dataInputStream.readFully(data);
            String str =new String(data);
            System.out.println("获取的数据类型为："+topStr);
            System.out.println("获取的数据内容为："+str);
        }
    }
}
