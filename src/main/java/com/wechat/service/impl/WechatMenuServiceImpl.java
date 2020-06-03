package com.wechat.service.impl;

import com.wechat.config.Config;
import com.wechat.pojo.vo.WechatMenuButtonVO;
import com.wechat.service.WechatAccessTokenService;
import com.wechat.service.WechatMenuService;
import com.wechat.util.HttpUtil;
import com.wechat.util.JsonUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class WechatMenuServiceImpl implements WechatMenuService {

    @Resource
    private Config config;

    @Resource
    private WechatAccessTokenService wechatAccessTokenService;

    @Override
    public String upgradeMenu(String menuStr) {
        String accessToken = wechatAccessTokenService.getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            return "get access token fail";
        }
        String url = config.getMenuUrl();
        url = String.format(url, accessToken);
        Map<String, WechatMenuButtonVO> map = JsonUtil.toObj(menuStr, Map.class);
        return HttpUtil.post(url, map, String.class);
    }

    @Override
    public String getMenu() {
        String accessToken = wechatAccessTokenService.getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            return "get access token fail";
        }

        String url = config.getGetMenuUrl();
        url = String.format(url, accessToken);
        return HttpUtil.get(url, String.class);
    }

    @Override
    public String delMenu() {
        String accessToken = wechatAccessTokenService.getAccessToken();
        if (StringUtils.isEmpty(accessToken)) {
            return "get access token fail";
        }

        String url = config.getDelMenuUrl();
        url = String.format(url, accessToken);
        return HttpUtil.get(url, String.class);
    }
}
