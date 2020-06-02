package com.wechat.pojo.entity;

import lombok.Data;

@Data
public class WechatAccessToken {
    private Integer id;
    private String accessToken;
    private Long expire;
}
