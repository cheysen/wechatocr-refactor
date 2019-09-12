package wechatocr.portal.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wechatocr.portal.service.fallback.OcrHistoryFallBack;

/**
 * @Author Cheysen
 * @Description 图片文字识别历史记录
 * @Date 2019/9/8 23:35
 */
@FeignClient(value = "wechatocr-history",fallback = OcrHistoryFallBack.class)
public interface OcrHistoryService {
    /** 文字识别历史记录查询接口
     * @param openId 用户id
     * @return
     */
    @GetMapping("/history/{openId}")
    String getOcrHistory(@PathVariable String openId);
}
