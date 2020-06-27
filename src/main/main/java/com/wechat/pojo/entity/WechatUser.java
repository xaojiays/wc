package com.wechat.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WechatUser {
    private Integer id;
    @JsonProperty("open_id")
    private String openId;
    private Boolean subscribe;
    private String nickname;
    private String sex;
    private String city;
    private String country;
    private String province;
    private String language;
    @JsonProperty("headimgurl")
    private String headImgUrl;
    @JsonProperty("subscribe_time")
    private Integer subscribeTime;
    private String unionId;
    private Integer createdAt;
    private Integer updatedAt;
}
