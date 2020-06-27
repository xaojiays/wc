package com.wechat.consts;

public enum WechatMsgType {
    TEXT("text"),
    EVENT("event"),
    IMAGE("image"),
    VOICE("voice"),
    SHORT_VIDEO("shortvideo"),
    LOCATION("location"),
    LINK("link");

    String val;

    WechatMsgType(String val) {
        this.val = val;
    }

    public static WechatMsgType get(String val) {
        if (val == null) {
            return null;
        }
        for (WechatMsgType typeEnum : WechatMsgType.values()) {
            if (typeEnum.val.equals(val)) {
                return typeEnum;
            }
        }
        return null;
    }
}
