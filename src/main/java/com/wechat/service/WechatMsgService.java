package com.wechat.service;

public interface WechatMsgService {

    /**
     * wechat token verify
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echoStr
     * @return
     */
    String verify(String signature, String timestamp, String nonce, String echoStr);

    /**
     * reply custom msg by wechat msgType
     * @param msg
     * @param msgSignature
     * @param timestamp
     * @param nonce
     * @return
     */
    String reply(String msg, String msgSignature, String timestamp, String nonce);
}
