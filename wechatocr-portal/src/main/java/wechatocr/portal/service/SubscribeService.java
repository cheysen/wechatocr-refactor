package wechatocr.portal.service;

import org.apache.commons.mail.EmailException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import wechatocr.domain.message.response.TextMessage;
import wechatocr.portal.service.fallback.SubscribeFallBack;

/**
 * @Author Cheysen
 * @Description 订阅与取关事件服务接口
 * @Date 2019/9/2 1:45
 */
@FeignClient(value = "wechat-picture-text",fallback = SubscribeFallBack.class)
public interface SubscribeService {

    /** 订阅事件服务接口
     * @param textMsg
     * @param fromUserName
     * @return
     * @throws EmailException
     */
    @PostMapping(value = "/subscribe")
    String subscirbe(@RequestBody TextMessage textMsg, @RequestParam(value = "fromUserName") String fromUserName) throws EmailException;

    /** 取关事件服务接口
     * @param fromUserName 用户名
     * @return
     * @throws EmailException
     */
    @GetMapping(value = "/unsubscribe")
    String unsubscirbe(@RequestParam(value = "fromUserName") String fromUserName) throws EmailException;
}
