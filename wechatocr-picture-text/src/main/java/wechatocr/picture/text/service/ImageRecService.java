package wechatocr.picture.text.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wechatocr.domain.message.response.TextMessage;
import java.util.Map;

/**
 * @Author Cheysen
 * @Description 图片识别接口
 * @Date 2019/8/29 23:33
 */
public interface ImageRecService {
    Logger logger = LoggerFactory.getLogger(ImageRecService.class);
    /** 图片文字识别
     * @param reqMap
     * @param msgId
     * @param textMessage
     * @return
     */
    String textRecognition(Map<String, String> reqMap, String msgId, TextMessage textMessage);

}
