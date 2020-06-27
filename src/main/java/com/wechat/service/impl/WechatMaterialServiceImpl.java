package com.wechat.service.impl;

import com.wechat.config.Config;
import com.wechat.pojo.ro.UploadImgRO;
import com.wechat.pojo.vo.WechatUploadImgVO;
import com.wechat.service.WechatAccessTokenService;
import com.wechat.service.WechatMaterialService;
import com.wechat.util.HttpUtil;
import com.wechat.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Service
@Slf4j
public class WechatMaterialServiceImpl implements WechatMaterialService {

    @Autowired
    private WechatAccessTokenService wechatAccessTokenService;

    @Autowired
    private Config config;

    @Override
    public String addMaterial() {
        return null;
    }

    @Override
    public String uploadImg(MultipartFile file) {
        String accessToken = wechatAccessTokenService.getAccessToken();
        if (accessToken == null) {
            return "get access token fail";
        }

        Resource resource = file.getResource();
        String url = config.getUploadImgUrl();
        url = String.format(url, accessToken);
        String res = HttpUtil.uploadImgToWechat(url, resource, String.class);
        WechatUploadImgVO vo = JsonUtil.toObj(res, WechatUploadImgVO.class);
        if (vo != null) {
            UploadImgRO ro = new UploadImgRO();
            ro.setErrno(0);
            ro.setData(Arrays.asList(vo.getUrl()));
            return JsonUtil.toJson(ro);
        }
        return res;
    }
}
