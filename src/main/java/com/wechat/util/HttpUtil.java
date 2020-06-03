package com.wechat.util;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

public class HttpUtil {

    public static <T> T get(String url, Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, clazz);
    }

    public static <T> T post(String url, Object body, Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate.postForObject(url, JsonUtil.toJson(body), clazz);
    }
}
