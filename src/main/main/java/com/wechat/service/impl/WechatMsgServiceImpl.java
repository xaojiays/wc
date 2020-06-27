package com.wechat.service.impl;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.wechat.config.Config;
import com.wechat.consts.WechatMsgType;
import com.wechat.pojo.entity.WechatUser;
import com.wechat.pojo.vo.WechatMsg;
import com.wechat.service.WechatMsgService;
import com.wechat.service.WechatUserService;
import com.wechat.util.JsonUtil;
import com.wechat.util.WechatMsgCrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class WechatMsgServiceImpl implements WechatMsgService {

    @Resource
    private Config config;

    @Resource
    private WechatUserService wechatUserService;

    @Override
    public String verify(String signature, String timestamp, String nonce, String echoStr) {
        String[] arr = {config.getToken(), timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        String sign = DigestUtils.sha1Hex(sb.toString());
        return sign.equals(signature) ? echoStr : "";
    }

    @Override
    public String reply(String msg, String msgSignature, String timestamp, String nonce) {
        try {
            WechatMsg wechatMsg;
            if (config.getEncrypt()) {
                WechatMsgCrypt crypt = new WechatMsgCrypt(config.getToken(), config.getAesKey(), config.getAppId());
                String decryptMsg = crypt.decryptMsg(msgSignature, timestamp, nonce, msg);
                wechatMsg = getWechatMsg(decryptMsg);
            } else {
                wechatMsg = getWechatMsg(msg);
            }
            log.info(JsonUtil.toJson(wechatMsg));
            if (wechatMsg == null) {
                return "";
            }

            WechatMsgType msgType = WechatMsgType.get(wechatMsg.getMsgType());
            if (msgType == null) {
                return "";
            }

            String replyMsg = null;
            switch (msgType) {
                case TEXT:
                    replyMsg = textProcess(wechatMsg);
                    break;
                case EVENT:
                    replyMsg = eventProcess(wechatMsg);
                    break;
                case LINK:
                case IMAGE:
                case VOICE:
                case LOCATION:
                case SHORT_VIDEO:
                default:
                    break;
            }
            return replyMsg;
        } catch (Exception e) {
            log.error("reply msg error", e);
        }
        return "";
    }

    private WechatMsg getWechatMsg(String msg) {
        XStream xstream = initXStream();
        xstream.ignoreUnknownElements();
        xstream.alias("xml", WechatMsg.class);
        xstream.aliasField("ToUserName", WechatMsg.class, "toUserName");
        xstream.aliasField("FromUserName", WechatMsg.class, "fromUserName");
        xstream.aliasField("CreateTime", WechatMsg.class, "createTime");
        xstream.aliasField("MsgType", WechatMsg.class, "msgType");
        xstream.aliasField("Content", WechatMsg.class, "content");
        xstream.aliasField("MsgId", WechatMsg.class, "msgId");
        xstream.aliasField("PicUrl", WechatMsg.class, "picUrl");
        xstream.aliasField("MediaId", WechatMsg.class, "mediaId");
        xstream.aliasField("Format", WechatMsg.class, "format");
        xstream.aliasField("Recognition", WechatMsg.class, "recognition");
        xstream.aliasField("ThumbMediaId", WechatMsg.class, "thumbMediaId");
        xstream.aliasField("Location_X", WechatMsg.class, "locationX");
        xstream.aliasField("Location_Y", WechatMsg.class, "locationY");
        xstream.aliasField("Scale", WechatMsg.class, "scale");
        xstream.aliasField("Label", WechatMsg.class, "label");
        xstream.aliasField("Title", WechatMsg.class, "title");
        xstream.aliasField("Description", WechatMsg.class, "description");
        xstream.aliasField("Url", WechatMsg.class, "url");
        xstream.aliasField("Event", WechatMsg.class, "event");
        xstream.aliasField("EventKey", WechatMsg.class, "eventKey");
        xstream.aliasField("MenuId", WechatMsg.class, "menuId");
        return (WechatMsg) xstream.fromXML(msg);
    }

    private XStream initXStream() {
        XStream xstream = new XStream(
                new XppDriver() {
                    public HierarchicalStreamWriter createWriter(Writer out) {
                        return new PrettyPrintWriter(out) {
                            protected void writeText(QuickWriter writer, String text) {
                                if (text.startsWith("<![CDATA[")
                                        && text.endsWith("]]>")) {
                                    writer.write(text);
                                } else {
                                    super.writeText(writer, text);
                                }
                            }
                        };
                    }

                    ;
                }
        );
        return xstream;
    }

    private String textProcess(WechatMsg wechatMsg) {
        return getTextReplyMsg(wechatMsg, "hello");
    }

    private String eventProcess(WechatMsg wechatMsg) {
        if (StringUtils.isEmpty(wechatMsg.getEvent())) {
            return getDefaultReply(wechatMsg);
        }

        switch (wechatMsg.getEvent().toLowerCase()) {
            case "subscribe":
                return subscribeMsg(wechatMsg);
            case "scan":
                return scanMsg(wechatMsg);
            case "unsubscribe":
                break;
            default:
                break;
        }
        return "";
    }

    private String subscribeMsg(WechatMsg msg) {
        WechatUser user = wechatUserService.getWechatUserFromWechat(msg.getFromUserName());
        if (user != null) {
            wechatUserService.upgrade(user);
        }
        String text = "你好,欢迎你订阅买前查一查.\n打卡输入框,复制天猫或者淘宝的链接发送给我们,即可获取相关优惠信息";
        return getTextReplyMsg(msg, text);
    }

    private String scanMsg(WechatMsg msg) {
        String text = "欢迎回来";
        return getTextReplyMsg(msg, text);
    }

    private String getDefaultReply(WechatMsg wechatMsg) {
        return getTextReplyMsg(wechatMsg,"word");
    }

    private String getTextReplyMsg(WechatMsg msg, String str) {
        String res = "<xml>";
        res += "<ToUserName><![CDATA[" + msg.getFromUserName() + "]]></ToUserName>";
        res += "<FromUserName><![CDATA[" + msg.getToUserName() + "]]></FromUserName>";
        res += "<CreateTime>" + System.currentTimeMillis() / 1000 + "</CreateTime>";
        res += "<MsgType><![CDATA[text]]></MsgType>";
        res += "<Content><![CDATA[" + str + "]]></Content>";
        res += "</xml>";
        return res;
    }

    private String getImgReplyMsg(WechatMsg msg, List<String> mediaIds) {
        String res = "<xml>";
        res += "<ToUserName><![CDATA[" + msg.getFromUserName() + "]]></ToUserName>";
        res += "<FromUserName><![CDATA[" + msg.getToUserName() + "]]></FromUserName>";
        res += "<CreateTime>" + System.currentTimeMillis() / 1000 + "</CreateTime>";
        res += "<MsgType><![CDATA[text]]></MsgType>";
        res += "<Image>";
        for (String mediaId : mediaIds) {
            res += "<MediaId>" + mediaId + "</MediaId>";
        }
        res += "</Image>";
        res += "</xml>";
        return res;
    }

    private String getArticleReplyMsg(WechatMsg msg, String articles) {
        return "";
    }
}
