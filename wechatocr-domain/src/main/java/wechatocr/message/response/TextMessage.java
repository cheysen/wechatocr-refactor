package wechatocr.message.response;

import lombok.Data;

/**
 * @Author Cheysen
 * @Description 响应文本消息
 * @Date 2019/7/24 14:28
 * @Version 1.0
 */
@Data
public class TextMessage extends BaseResponseMessage{
    /**
     * 回复的消息内容
     */
    private String Content;
}
