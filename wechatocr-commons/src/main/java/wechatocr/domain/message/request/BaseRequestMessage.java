package wechatocr.domain.message.request;

import lombok.Data;

/**
 * @Author Cheysen
 * @Description 微信请求消息基类
 * @Date 2019/8/28 23:10
 * @Version 1.0
 */
@Data
public class BaseRequestMessage {
    /**
     *开发者微信号
     */
    private String ToUserName;

    /**
     * 发送法账号(一个OpenID)
     */
    private String FromUserName;

    /**
     * 消息创建时间(整型)
     */
    private long CreateTime;
    /**
     * 消息类型
     */
    private String MsgType;
    /**
     * 消息id,64位整型
     */
    private long MsgId;
}
