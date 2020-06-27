package com.wechat.pojo.ro;

import lombok.Data;

import java.util.List;

@Data
public class UploadImgRO {
    private Integer errno;
    private List<String> data;
}
