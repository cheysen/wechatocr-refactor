package wechatocr.domain.message.request;

import lombok.Data;

/**
 * @Author Cheysen
 * @Description 文本消息
 * @Date 2019/7/24 14:16
 * @Version 1.0
 */
@Data
public class TextMessage extends BaseRequestMessage {
    /**
     * 请求的消息内容
     */
    private String Content;
}
