package wechatocr.service.baidu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Cheysen
 * @Description 百度云识别服务
 * @Date 2019/8/28 16:12
 */
public interface BaiDuOcrService {
    Logger logger = LoggerFactory.getLogger(BaiDuOcrService.class);

    /** 请求百度API高精度版文字识别
     * @param url 请求URL
     * @return
     */
    String postAccurateBasic(String url);

}
