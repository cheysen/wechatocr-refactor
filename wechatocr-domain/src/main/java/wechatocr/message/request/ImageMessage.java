package wechatocr.message.request;

import lombok.Data;

/**
 * @Author Cheysen
 * @Description 图片消息
 * @Date 2019/7/24 14:17
 * @Version 1.0
 */
@Data
public class ImageMessage extends BaseRequestMessage{
    /**
     * 请求图片URL
     */
    private String PicUrl;
}
