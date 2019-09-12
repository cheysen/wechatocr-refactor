package wechatocr.message.response;

import lombok.Data;

/**
 * @Author Cheysen
 * @Description 返回给微信的消息基类
 * @Date 2019/8/28 23:13
 * @Version 1.0
 */
@Data
public class BaseResponseMessage {
    /**
     * 接受方账号(收到的OpenID)
     */
    private String ToUserName;
    /**
     * 开发者微信号
     */
    private String FromUserName;
    /**
     * 消息创建时间
     */
    private long CreateTime;
    /**
     * 消息类型
     */
    private String MsgType;
}
