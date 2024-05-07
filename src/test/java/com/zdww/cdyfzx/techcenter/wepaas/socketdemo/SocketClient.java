package com.zdww.cdyfzx.techcenter.wepaas.socketdemo;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    public static void main2(String[] args) throws IOException {
        Socket socket =new Socket("127.0.0.1",9999);
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream =new DataOutputStream(outputStream);
        Scanner scanner =new Scanner(System.in);
        if(scanner.hasNext()){
            String str = scanner.next();
            byte[] data = str.getBytes();
            dataOutputStream.write(("{1,"+data.length+"}").getBytes());

            dataOutputStream.write(data);
            dataOutputStream.flush();

            dataOutputStream.write(("阿达伟大{1,"+data.length+"}").getBytes());

            dataOutputStream.write(data);
            dataOutputStream.flush();
        }
        socket.shutdownOutput();
        socket.close();
    }

    public static void main(String[] args) {
        byte[] bytes = "{1,2147483647}".getBytes();
        for (byte a : bytes
        ) {
            System.out.println(a);
        }
        System.out.println(bytes);
        System.out.println(Integer.MAX_VALUE);

        byte[] bytes1 = intToBytes2(2);

        for (byte a : bytes1
        ) {
            System.out.println(a);
        }
    }

    /**
     * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在前，低位在后)的顺序。  和bytesToInt2（）配套使用
     */
    public static byte[] intToBytes2(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
     */
    public static int bytesToInt2(byte[] src, int offset) {
        int value = (((src[offset] & 0xFF) << 24) | ((src[offset + 1] & 0xFF) << 16) | ((src[offset + 2] & 0xFF) << 8) | (src[offset + 3] & 0xFF));
        return value;
    }
}
