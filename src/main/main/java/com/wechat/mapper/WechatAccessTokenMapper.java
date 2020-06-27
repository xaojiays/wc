package com.wechat.mapper;

import com.wechat.pojo.entity.WechatAccessToken;
import org.apache.ibatis.annotations.Param;

public interface WechatAccessTokenMapper {

    int insert(@Param("pojo") WechatAccessToken pojo);

    WechatAccessToken latestOne();
}
