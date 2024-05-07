//package com.zdww.cdyfzx.techcenter.wepaas.controller;
//
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import sun.misc.BASE64Encoder;
//
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.security.SecureRandom;
//import java.security.Security;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@SuppressWarnings("all")
//public class BeiFenThreeDES {
//    private static final String KEY = "jmzfckeybnxlqjqj"; // key必须16位
//
//    final static java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
//
//    static {
//        try {
//            Security.addProvider(new BouncyCastleProvider());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 根据密钥对指定的明文content进行加密.
//     *
//     * @param content 明文
//     * @return 加密后的密文.
//     */
//    public static String encrypt(String content) {
//        try {
//            KeyGenerator kgen = KeyGenerator.getInstance("AES");
//            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
//            random.setSeed(KEY.getBytes());
//            kgen.init(128, random);
//            SecretKey secretKey = kgen.generateKey();
////            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");
//            Cipher cipher = Cipher.getInstance("AES/GCM/PKCS5Padding", "BC");
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
//            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
//            return new BASE64Encoder().encode(encrypted);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void main(String[] args) {
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String json = "DoItNewId&DoItNewName&" + formatter.format(date);
//        System.out.println(encrypt(json));
//    }
//}