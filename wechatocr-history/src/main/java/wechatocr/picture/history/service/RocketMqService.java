package wechatocr.picture.history.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;
import wechatocr.domain.data.object.UserOcrHistory;
import wechatocr.mapper.mymapper.UserOcrHistoryMapper;
import wechatocr.utils.JacksonUtils;

/**
 * @Author Cheysen
 * @Description 监听消息队列
 * @Date 2019/9/8 16:54
 * @Version 1.0
 */
@Service
public class RocketMqService {
    private Logger logger = LoggerFactory.getLogger(RocketMqService.class);
    @Autowired
    private UserOcrHistoryMapper userOcrHistoryMapper;
    @StreamListener("input")
    public void insert(String ocrResult){
        try {
            UserOcrHistory userOcrHistory = JacksonUtils.json2pojo(ocrResult,UserOcrHistory.class);
            userOcrHistoryMapper.insert(userOcrHistory);
            logger.info("消费消息，将消息{}插入数据库",userOcrHistory.getCreateTime());
        } catch (Exception e) {
            logger.error("消费消息时异常");
            e.printStackTrace();
        }
    }
}
