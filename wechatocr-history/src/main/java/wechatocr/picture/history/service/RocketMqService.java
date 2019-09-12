package wechatocr.picture.history.service;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import wechatocr.data.object.UserOcrHistory;

/**
 * @Author Cheysen
 * @Description 监听消息队列
 * @Date 2019/9/8 16:54
 * @Version 1.0
 */
@Service
public class RocketMqService {
    @StreamListener("input")
    public void insert(String ocrResult){
        UserOcrHistory userOcrHistory = Mapp
    }
}
