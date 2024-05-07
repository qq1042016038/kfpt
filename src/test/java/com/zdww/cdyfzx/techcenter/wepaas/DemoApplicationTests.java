package com.zdww.cdyfzx.techcenter.wepaas;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

class DemoApplicationTests {
    public static void main(String[] args) throws IOException {
        System.out.println(FileUtils.readFileToString(new File("C:\\Users\\gf\\小说/三九音域-我在精神病院学斩神.txt"),"utf-8"));
    }
}
