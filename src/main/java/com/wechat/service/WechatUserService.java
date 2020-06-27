package com.wechat.service;

import com.wechat.pojo.entity.WechatUser;

import java.util.List;

public interface WechatUserService {

    /**
     * get wechat user info from wechat by open id
     * @param openId
     * @return
     */
    WechatUser getWechatUserFromWechat(String openId);

    /**
     * create or update wechat user
     * @param wechatUser
     * @return
     */
    void upgrade(WechatUser wechatUser);

    /**
     * get wechat users of page
     * @param page
     * @return
     */
    List<WechatUser> getWechatUsers(Integer page);
}
