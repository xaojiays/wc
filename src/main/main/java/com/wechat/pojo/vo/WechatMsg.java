package com.wechat.pojo.vo;

import lombok.Data;

@Data
public class WechatMsg {
    private String toUserName;
    private String fromUserName;
    private Integer createTime;
    private String msgType;
    private String event;
    private String eventKey;
    private String content;
    private Long msgId;
    private String picUrl;
    private String mediaId;
    private String format;
    private String recognition;
    private String thumbMediaId;
    private String locationX;
    private String locationY;
    private Integer scale;
    private String label;
    private String title;
    private String description;
    private String url;
    private Long menuId;
}
