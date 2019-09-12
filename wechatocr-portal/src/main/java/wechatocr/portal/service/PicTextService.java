package wechatocr.portal.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author Cheysen
 * @Description 一般行图片文字识别接口
 * @Date 2019/8/29 22:59
 */
@FeignClient(value = "wechat-picture-text")
public interface PicTextService {
}
