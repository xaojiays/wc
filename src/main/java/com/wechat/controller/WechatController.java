package com.wechat.controller;

import com.wechat.service.WechatMaterialService;
import com.wechat.service.WechatMenuService;
import com.wechat.service.WechatMsgService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wechat/")
public class WechatController {

    @Resource
    private WechatMsgService wechatMsgService;

    @Resource
    private WechatMenuService wechatMenuService;

    @Resource
    private WechatMaterialService wechatMaterialService;

    @GetMapping("callback")
    public String verify(@RequestParam(value = "signature", required = false, defaultValue = "") String signature,
                         @RequestParam(value = "timestamp", required = false, defaultValue = "") String timestamp,
                         @RequestParam(value = "nonce", required = false, defaultValue = "") String nonce,
                         @RequestParam(value = "echostr", required = false, defaultValue = "") String echoStr) {
        return wechatMsgService.verify(signature, timestamp, nonce, echoStr);
    }

    @PostMapping("callback")
    public String reply(@RequestBody String wechatEncryptMsg,
                        @RequestParam(value = "msg_signature", required = false, defaultValue = "") String msgSignature,
                        @RequestParam(value = "timestamp", required = false, defaultValue = "") String timestamp,
                        @RequestParam(value = "nonce", required = false, defaultValue = "") String nonce) {
        System.out.println(wechatEncryptMsg);
        return wechatMsgService.reply(wechatEncryptMsg, msgSignature, timestamp, nonce);
    }

    @PostMapping("upgrade-menu")
    public String upgradeMenu(@RequestBody String menuStr) {
        return wechatMenuService.upgradeMenu(menuStr);
    }

    @GetMapping("get-menu")
    public String getMenu() {
        return wechatMenuService.getMenu();
    }

    @GetMapping("del-menu")
    public String delMenu() {
        return wechatMenuService.delMenu();
    }

    @GetMapping("get-materials")
    public String getMaterials() {
        return "";
    }

    @PostMapping("add-material")
    public String addMaterial() {
        return wechatMaterialService.addMaterial();
    }

    @PostMapping("upload-img")
    public String uploadImg(@RequestParam("file") MultipartFile file) {
        return wechatMaterialService.uploadImg(file);
    }
}
