package com.wechat.service.impl;

import com.wechat.config.Config;
import com.wechat.mapper.WechatUserMapper;
import com.wechat.pojo.entity.WechatUser;
import com.wechat.service.WechatAccessTokenService;
import com.wechat.service.WechatUserService;
import com.wechat.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class WechatUserServiceImpl implements WechatUserService {

    @Resource
    private WechatUserMapper wechatUserMapper;

    @Resource
    private WechatAccessTokenService wechatAccessTokenService;

    @Resource
    private Config config;

    @Override
    public void upgrade(WechatUser wechatUser) {
        if (wechatUserMapper.exist(wechatUser)) {
            wechatUserMapper.update(wechatUser);
            return;
        }
        wechatUserMapper.insert(wechatUser);
    }

    @Override
    public WechatUser getWechatUserFromWechat(String openId) {
        String accessToken = wechatAccessTokenService.getAccessToken();
        if (accessToken == null) {
            log.error("get access token fail");
            return null;
        }

        String url = config.getUserUrl();
        url = String.format(url, accessToken, openId);
        WechatUser user = HttpUtil.get(url, WechatUser.class);
        user.setOpenId(openId);
        if (user == null) {
            user.setCreatedAt((int)(System.currentTimeMillis() / 1000));
        }
        user.setUpdatedAt((int)(System.currentTimeMillis() / 1000));
        return user;
    }

    @Override
    public List<WechatUser> getWechatUsers(Integer page) {
        final int size = 50;
        final int offset = (page - 1) * size;
        return wechatUserMapper.getWechatUsers(offset, size);
    }
}
