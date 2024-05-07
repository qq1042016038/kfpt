package com.zdww.cdyfzx.techcenter.wepaas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeSet;

public class LeeCode {
    static int[] num = new int[]{3, 1, 9, 5, 11, 24, 6, 96, 77, 33};
    static int[] a = new int[]{1, 3, 5, 7, 9};
    static int[] b = new int[]{2, 4, 6, 8,10};

    public static String longestPalindrome(String s) {
        s = "ccc";
        int length = 0;
        int index = 0;

        for (int i = 0; i < s.length(); i++) {
            for (int j = s.length()-1; j > i; j--) {
                int x = j - i;
                if (s.charAt(i)==s.charAt(j)) {
                    if (x > length) {
                        length = x;
                        index = i;
                    }
                    break;
                }
            }
        }
        System.out.println(index+"   "+length);
        System.out.println(s.substring(index,index+length+1));
        return s.substring(index,s.length()-length);
    }

    String s = "### nginx ingress\n"
            + "#server_name\n"
            + "proxy.name=_ \n"
            + "#listen\n"
            + "proxy.port=80\n"
            + "proxy.protocol=https\n"
            + "#keepalive_timeout\n"
            + "proxy.timeout=10\n"
            + "#client_max_body_size\n"
            + "proxy.uploadSize=100M\n"
            + "proxy.location[0].path=/bidding\n"
            + "#Prefix Exact (=)\n"
            + "proxy.location[0].pathType=Exact\n"
            + "proxy.location[0].protocol=http\n"
            + "#proxy_pass\n"
            + "proxy.location[0].address=http://172.17.0.1\n"
            + "proxy.location[0].port=80\n"
            + "#redirect\n"
            + "proxy.location[0].redirect=^(.*) http://login.hxyc.com.cn/cas break;\n"
            + "#rewrite\n"
            + "proxy.location[0].rewrite=^(.*) http://login.hxyc.com.cn/cas break;\n"
            + "#proxy_set_header\n"
            + "proxy.location[0].header[0]=Host $host\n"
            + "proxy.location[0].header[1]=X-Real-IP $remote_addr\n"
            + "proxy.location[0].header[2]=REMOTE-HOST $remote_addr\n"
            + "### nginx\n"
            + "#alias\n"
            + "proxy.location[0].alias=/web/\n"
            + "#index\n"
            + "proxy.location[0].index=index.html\n"
            + "proxy.location[0].try_files=$uri $uri/ /index.html\n"
            + "proxy.upstream[0].name = backend\n"
            + "proxy.upstream[0].address[0] = http://172.17.0.1\n"
            + "proxy.upstream[0].port[0] =80\n"
            + "proxy.upstream[0].weight[0]=1\n"
            + "proxy.upstream[0].address[1] = http://172.17.0.2\n"
            + "proxy.upstream[0].port[1] =8081\n"
            + "proxy.upstream[0].weight[1]=2";

    //滑动窗口
    public static int lengthOfLongestSubstring(String s) {
        s = " ";
        int length = 0;
        int index = 0;
        if (s.length() == 0 || s.length() == 1) {
            return s.length();
        }

        int flag = 1;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length(); j++) {
                char c = s.charAt(j);
                int x = j - i;
                if (s.substring(i, j).indexOf(c)>-1) {
                    if (x > length) {
                        length = x;
                        index = i;
                    }
                    break;
                }
                if (j == s.length() - 1) {
                    length = Math.max(x+1,length);
                }
            }
            if(i+length>s.length()){
                break;
            }
        }
        System.out.println(index);
        return length;
    }

    public static void main(String[] args) throws IOException {
        Process exec = Runtime.getRuntime().exec(new String[]{ "cmd", "/c", "java -DnacosDataId=pipleline-vue"
                + ".properties -DnacosGroup=DEFAULT_GROUP -jar "
                + "C:\\Users\\gf\\wanwei\\nginx"
                + "-conf\\out\\artifacts\\nginx_conf_jar\\nginx-conf.jar"});
        InputStream inputStream = exec.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }

//        Scanner in = new Scanner(System.in);
//        // 注意 hasNext 和 hasNextLine 的区别
//        String x = in.nextLine();
//        System.out.println(Integer.parseInt(x.substring(2,x.length()),16));

//        quer(num, 0, num.length - 1);
//        System.out.println(Arrays.toString(sort(a,b)));
//        System.out.println(lengthOfLongestSubstring(""));
//        longestPalindrome("");

    }

    public static void quer(int[] a, int start, int end) {
        if (start >= end) {
            return;
        }
        int mid = (start + end) / 2;
        quer(a, start, mid);
        quer(a, mid + 1, end);
        quer(a, start, mid, end);
    }

    public static void quer(int[] a,int start,int mid,int end){
        int [] c = new int[end - start +1];
        int i = start;
        int j = mid+1;
        int k = end;
        int z = 0;
        while (i <= mid && j <= k) {
            if (a[i] < a[j]) {
                c[z] = a[i];
                i++;
            } else {
                c[z] = a[j];
                j++;
            }
            z++;
        }

        while (i <= mid) {
            c[z] = a[i];
            i++;
            z++;
        }

        while (j <= end) {
            c[z] = a[j];
            j++;
            z++;
        }
        System.out.println(Arrays.toString(c));
        for(int k2 = 0 ; k2 < c.length ; k2++){
            a[k2+start] = c[k2];
        }
    }

    public static int[] sort(int[] a, int[] b){
        int[] c = new int[a.length+b.length];
        int aNum = 0,bNum = 0,cNum=0;
        while(aNum<a.length && bNum < b.length){
            if(a[aNum] >= b[bNum]){//比较a数组和b数组的元素，谁更小将谁赋值到c数组
                c[cNum++] = b[bNum++];
            }else{
                c[cNum++] = a[aNum++];
            }
        }
//如果a数组全部赋值到c数组了，但是b数组还有元素，则将b数组剩余元素按顺序全部复制到c数组
        while(aNum == a.length && bNum < b.length){
            c[cNum++] = b[bNum++];
        }
//如果b数组全部赋值到c数组了，但是a数组还有元素，则将a数组剩余元素按顺序全部复制到c数组
        while(bNum == b.length && aNum < a.length){
            c[cNum++] = a[aNum++];
        }
        return c;
    }
}
