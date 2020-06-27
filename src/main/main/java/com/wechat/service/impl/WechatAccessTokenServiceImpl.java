package com.wechat.service.impl;

import com.wechat.config.Config;
import com.wechat.mapper.WechatAccessTokenMapper;
import com.wechat.pojo.entity.WechatAccessToken;
import com.wechat.pojo.vo.WechatAccessTokenVO;
import com.wechat.service.WechatAccessTokenService;
import com.wechat.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class WechatAccessTokenServiceImpl implements WechatAccessTokenService {

    @Resource
    private WechatAccessTokenMapper wechatAccessTokenMapper;

    @Resource
    private Config config;

    @Override
    public String getAccessToken() {
        WechatAccessToken token = wechatAccessTokenMapper.latestOne();
        if (token != null && token.getExpire() - System.currentTimeMillis() > 1000 * 60) {
            return token.getAccessToken();
        }

        token = getFromWechat();
        if (token == null) {
            return null;
        }
        wechatAccessTokenMapper.insert(token);
        return token.getAccessToken();
    }

    private WechatAccessToken getFromWechat() {
        String url = config.getAccessTokenUrl();
        url = String.format(url, config.getAppId(), config.getSecret());
        WechatAccessTokenVO vo = HttpUtil.get(url, WechatAccessTokenVO.class);
        if (vo == null) {
            log.info("get wechat access token fail");
            return null;
        }

        WechatAccessToken token = new WechatAccessToken();
        token.setAccessToken(vo.getAccessToken());
        token.setExpire(System.currentTimeMillis() + vo.getExpiresIn() * 1000);
        return token;
    }
}
