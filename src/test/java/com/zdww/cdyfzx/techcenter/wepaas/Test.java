package com.zdww.cdyfzx.techcenter.wepaas;

import lombok.SneakyThrows;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class Test {

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new StringReader("key=测试"));
        System.out.println(properties.getProperty("key"));
    }
    public static void main1(String[] args) {
        TestThread t = new TestThread();
        new Thread(t::test1).start();
        new Thread(t::test1).start();
        new Thread(t::test1).start();
        new Thread(t::test1).start();
        new Thread(t::test1).start();

        new Thread(t::test).start();
    }
}

class TestThread{
    volatile boolean flag = false;
    void test(){
        System.out.println("111111111");
        flag = true;
        System.out.println(System.currentTimeMillis());
    }
    @SneakyThrows
    void test1()  {
        while (!flag){
            System.out.println(flag);
            System.out.println("222222");
//            Thread.sleep(1);
        }
    }
}
