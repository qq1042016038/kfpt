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
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;



@RequestMapping("/xs")
@RestController
@Slf4j
public class XswitchContrroller {

    @RequestMapping("/test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        log.info("get sx body is =====:  {}",IOUtils.toString(inputStream));
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.getWriter().write("{\"action\": \"play\",\"file\": \"say:尊敬的客户，您好，请说一句话\",\"loops\": 3,\"breakable\": false,"
                + "\"asr_engine\": \"xunfei\",\"asr_grammar\":\"{accent=mandarin, barge-in=true, "
                + "start-input-timers=true,no-input-timeout=4000,speech-timeout=2500}default\",\"asr\": true,"
                + "\"next\": \"\",\"variables\": {\"tts_engine\" : \"xunfei\",\"tts_voice\" : \"default\"}}");
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
