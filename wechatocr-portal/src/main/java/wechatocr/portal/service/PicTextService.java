package wechatocr.portal.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import wechatocr.domain.message.response.TextMessage;
import wechatocr.portal.service.fallback.PicTextFallBack;

import java.util.Map;

/**
 * @Author Cheysen
 * @Description 一般行图片文字识别接口
 * @Date 2019/8/29 22:59
 */
@FeignClient(value = "wechat-picture-text",fallback = PicTextFallBack.class)
public interface PicTextService {

    /** 一般性图片内容文字识别服务接口
     * @param reqMap 请求消息Map
     * @param msgId 请求消息id
     * @param textMsg 返回的文本消息
     * @return
     */
    @GetMapping(value = "/text")
    String textInPicture(@RequestParam Map<String,String> reqMap, @RequestParam(value = "msgId") String msgId, @RequestBody TextMessage textMsg);
}
