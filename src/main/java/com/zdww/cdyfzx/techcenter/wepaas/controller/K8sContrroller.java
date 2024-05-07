package com.zdww.cdyfzx.techcenter.wepaas.controller;

import cn.hutool.core.text.CharSequenceUtil;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RequestMapping("/k8s")
@RestController
public class K8sContrroller {

    @RequestMapping("/test")
    public String test(@RequestParam(value = "stepJobId", required = false) String name,
                     MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(name);
        request.getHeader("Token");
        System.out.println(request.getHeader("Token"));



        Map<String, String> map = new HashMap<>();
        map.put("1","1");
        ThreadLocal<Map<String, String>> requestAttributesHolder = new NamedThreadLocal("Request attributes");
        requestAttributesHolder.set(map);
        List<String> list = Arrays.asList("1");
        list.parallelStream().forEach(i->{
            System.out.println(i);
            Map<String, String> stringStringMap = requestAttributesHolder.get();
            stringStringMap.toString();
            ServletRequestAttributes attributes;
            try {
                attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            } catch (IllegalStateException var3) {
                var3.printStackTrace();
                return;
            }

            HttpServletRequest request1 = attributes.getRequest();
            String token = (String) Optional.ofNullable(request1.getHeader("token")).orElse(request1.getParameter("token"));
            if (CharSequenceUtil.isEmpty(token) && null != request1.getSession().getAttribute("token")) {
                token = request1.getSession().getAttribute("token").toString();
            }

        });

        return name;
    }
}
