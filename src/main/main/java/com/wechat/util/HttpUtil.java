package com.wechat.util;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

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

    public static <T> T uploadImgToWechat(String url, Resource resource, Class<T> clazz) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", resource);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(parts, headers);
        return restTemplate.postForEntity(url, httpEntity, clazz).getBody();
    }
}
