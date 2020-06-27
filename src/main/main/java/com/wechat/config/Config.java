package com.wechat.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class Config {

    @Value("${encrypt}")
    private Boolean encrypt;

    @Value("${token}")
    private String token;

    @Value("${appId}")
    private String appId;

    @Value("${aesKey}")
    private String aesKey;

    @Value("${secret}")
    private String secret;

    @Value("${accessTokenUrl}")
    private String accessTokenUrl;

    @Value("${menuUrl}")
    private String menuUrl;

    @Value("${getMenuUrl}")
    private String getMenuUrl;

    @Value("${delMenuUrl}")
    private String delMenuUrl;

    @Value("${uploadImgUrl}")
    private String uploadImgUrl;

    @Value("${userUrl}")
    private String userUrl;
}
