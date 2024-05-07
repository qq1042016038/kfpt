package com.zdww.cdyfzx.techcenter.wepaas.controller;


import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES对称加密工具类
 *
 * @author 单红宇
 * @date 2019年7月18日
 *
 */
public class AESUtils {

    private static final Logger logger = LoggerFactory.getLogger(AESUtils.class);

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/GCM/PKCS5Padding";;// 默认的加密算法

    /**
     * AES 加密操作
     *
     * @param content     待加密内容
     * @param encryptPass 加密密码
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String encryptPass) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(encryptPass));
            byte[] iv = cipher.getIV();
            assert iv.length == 12;
            byte[] encryptData = cipher.doFinal(content.getBytes());
            assert encryptData.length == content.getBytes().length + 16;
            byte[] message = new byte[12 + content.getBytes().length + 16];
            System.arraycopy(iv, 0, message, 0, 12);
            System.arraycopy(encryptData, 0, message, 12, encryptData.length);
            return Base64.encodeBase64String(message);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                 | BadPaddingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * AES 解密操作
     *
     * @param base64Content
     * @param encryptPass
     * @return
     */
    public static String decrypt(String base64Content, String encryptPass) {
        byte[] content = Base64.decodeBase64(base64Content);
        if (content.length < 12 + 16){
            throw new IllegalArgumentException();
        }
        GCMParameterSpec params = new GCMParameterSpec(128, content, 0, 12);
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(encryptPass), params);
            byte[] decryptData = cipher.doFinal(content, 12, content.length - 12);
            return new String(decryptData);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                 | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 生成加密秘钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static SecretKeySpec getSecretKey(String encryptPass) throws NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        // 初始化密钥生成器，AES要求密钥长度为128位、192位、256位
        kg.init(128, new SecureRandom(encryptPass.getBytes()));
        SecretKey secretKey = kg.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
    }

    public static void main1(String[] args) {
        RestTemplate client = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //设置其他头信息

        HttpEntity<String> formEntity = new HttpEntity<String>("{\n"
                + "  \"feedbackParam\": {\n"
                + "    \"communicationSituation\": \"1\",\n"
                + "    \"customerExpectation\": \"1\",\n"
                + "    \"delResult\": \"1\",\n"
                + "    \"deptId\": \"1\",\n"
                + "    \"deptName\": \"1\",\n"
                + "    \"solution\": \"1\"\n"
                + "  },\n"
                + "  \"workBaseParam\": {\n"
                + "    \"status\": 1,\n"
                + "    \"type\": 1,\n"
                + "    \"uuid\": \"d50cc1cc56514435b920c4dad771319b\"\n"
                + "  }\n"
                + "}", headers);
        String body = client.postForEntity("http://localhost:8082/msbback/callback/receive", formEntity, String.class).getBody();
        System.out.println(body);
    }

//    public static void main(String[] args) {
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        JedisConnectionFactory fac = new JedisConnectionFactory(new JedisPoolConfig());
//        JedisShardInfo shardInfo = new JedisShardInfo("localhost", 6379);
//        fac.setShardInfo(shardInfo);
//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
//        redisTemplate.setConnectionFactory(fac);
//        redisTemplate.afterPropertiesSet();
//
//
//        redisTemplate.opsForList().rightPush("list","1");
//        redisTemplate.opsForList().rightPush("list","2");
//        redisTemplate.opsForList().rightPush("list","3");
//        System.out.println(redisTemplate.opsForList().indexOf("list", "4"));
//        System.out.println(redisTemplate.opsForList().range("list", 0, -1));
//
//    }
}