package wechatocr.portal.service.fallback;

import org.apache.commons.mail.EmailException;
import org.springframework.stereotype.Component;
import wechatocr.domain.message.response.TextMessage;
import wechatocr.portal.service.SubscribeService;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/9/5 15:01
 * @Version 1.0
 */
@Component
public class SubscribeFallBack implements SubscribeService {
    @Override
    public String subscirbe(TextMessage textMsg, String fromUserName) throws EmailException {
        return "success";
    }

    @Override
    public String unsubscirbe(String fromUserName) throws EmailException {
        return "success";
    }
}
