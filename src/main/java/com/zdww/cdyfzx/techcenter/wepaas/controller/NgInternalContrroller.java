package com.zdww.cdyfzx.techcenter.wepaas.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


@RequestMapping("/nginx")
@RestController
@Slf4j
public class NgInternalContrroller {

    @RequestMapping("/file")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        // 下载-返回给前台文件流
//        response.setHeader("Content-Type", "application/octet-stream");
//        //HTTP响应截断漏洞
////        response.setHeader("X-Accel-Redirect", URLEncoder.encode(XssCleanRuleUtils.xssClean(downloadResponse.getFilePath().replace(baseUrl, "")),
////                "UTF-8"));
//        response.setHeader("X-Accel-Buffering", "yes");//是否使用Nginx缓存，默认yes

        response.sendRedirect("http://localhost/");
    }

    public static void main(String[] args) throws Exception {
        String apiSecret = "NmM1NWMwNDYxNTY2ZGZiMTk5Y2MwYWU3";
        String apiKey = "eea0d26c5b793d8ed242e8cd53441bbd";

        URL url = new URL("https://tts-api.xfyun.cn/v2/tts");
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        StringBuilder builder = new StringBuilder("host: ").append(url.getHost()).append("\n").//
                append("date: ").append(date).append("\n").//
                append("GET ").append(url.getPath()).append(" HTTP/1.1");
        Charset charset = Charset.forName("UTF-8");
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(charset), "hmacsha256");
        mac.init(spec);
        byte[] hexDigits = mac.doFinal(builder.toString().getBytes(charset));
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        String authorization = String.format("hmac username=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);


        System.out.println("authorization ==" + Base64.getEncoder().encodeToString(authorization.getBytes(charset)));
        System.out.println("host ==" + url.getHost());
        System.out.println("date ==" + date);



    }
}
