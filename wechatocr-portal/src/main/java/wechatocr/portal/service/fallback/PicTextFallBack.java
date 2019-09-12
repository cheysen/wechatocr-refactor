package wechatocr.portal.service.fallback;

import org.springframework.stereotype.Component;
import wechatocr.domain.message.response.TextMessage;
import wechatocr.portal.service.PicTextService;

import java.util.Map;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/9/5 15:00
 * @Version 1.0
 */
@Component
public class PicTextFallBack implements PicTextService {
    @Override
    public String textInPicture(Map<String, String> reqMap, String msgId, TextMessage textMsg) {
        return "success";
    }

}
