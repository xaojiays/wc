package com.wechat.service;

import org.springframework.web.multipart.MultipartFile;

public interface WechatMaterialService {
    String addMaterial();

    /**
     * upload file to wechat
     * @param file
     * @return
     */
    String uploadImg(MultipartFile file);
}
