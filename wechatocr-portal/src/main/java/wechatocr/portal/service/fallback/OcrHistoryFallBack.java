package wechatocr.portal.service.fallback;

import org.springframework.stereotype.Component;
import wechatocr.portal.service.OcrHistoryService;


/**
 * @Author Cheysen
 * @Description
 * @Date 2019/9/8 23:37
 * @Version 1.0
 */
@Component
public class OcrHistoryFallBack implements OcrHistoryService {
    @Override
    public String getOcrHistory(String openId){
        return "success";
    }
}
