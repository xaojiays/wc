package com.wechat.controller;

import com.wechat.service.WechatAccessTokenService;
import com.wechat.service.WechatMsgService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wechat/")
public class WechatController {

    @Resource
    private WechatMsgService wechatMsgService;

    @Resource
    private WechatAccessTokenService wechatAccessTokenService;

    @GetMapping("callback")
    public String verify(@RequestParam(value = "signature", required = false, defaultValue = "") String signature,
                         @RequestParam(value = "timestamp", required = false, defaultValue = "") String timestamp,
                         @RequestParam(value = "nonce", required = false, defaultValue = "") String nonce,
                         @RequestParam(value = "echostr", required = false, defaultValue = "") String echoStr) {
        return wechatMsgService.verify(signature, timestamp, nonce, echoStr);
    }

    @PostMapping("callback")
    public String reply(@RequestBody String wechatEncryptMsg,
                        @RequestParam("msg_signature") String msgSignature,
                        @RequestParam("timestamp") String timestamp,
                        @RequestParam("nonce") String nonce) {
        return wechatMsgService.reply(wechatEncryptMsg, msgSignature, timestamp, nonce);
    }

    @PostMapping("menu")
    public String menu() {
        return wechatAccessTokenService.getAccessToken();
    }
}
