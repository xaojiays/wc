package com.wechat.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WechatMenuButtonVO {

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SubButton {
        private String name;
        private String type;
        private String key;
        private String url;
    }

    private String name;
    private String type;
    private String key;
    @JsonProperty("sub_button")
    private List<SubButton> subButtons;
}
