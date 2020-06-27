package com.wechat.mapper;

import com.wechat.pojo.entity.WechatUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WechatUserMapper {

    boolean exist(@Param("pojo") WechatUser pojo);

    int insert(@Param("pojo") WechatUser pojo);

    int update(@Param("pojo") WechatUser pojo);

    List<WechatUser> getWechatUsers(@Param("offset") int offset, @Param("size") int size);
}
