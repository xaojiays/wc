package com.wechat.controller;

import com.wechat.pojo.entity.WechatUser;
import com.wechat.service.WechatUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/mm/")
public class AdminController {

    @Resource
    private WechatUserService wechatUserService;

    @GetMapping("index")
    public String index(Model model, @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        List<WechatUser> users = wechatUserService.getWechatUsers(page);
        model.addAttribute("users", users);
        return "admin/index";
    }

    @GetMapping("article")
    public String index() {
        return "admin/article";
    }
}
