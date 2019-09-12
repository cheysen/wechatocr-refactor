package wechatocr.picture.text.service.impl;

import com.qiniu.common.Zone;
import com.qiniu.storage.model.DefaultPutRet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import wechatocr.constant.QiNiuKey;
import wechatocr.domain.data.object.UserOcrHistory;
import wechatocr.domain.message.response.TextMessage;
import wechatocr.picture.text.constant.PictureTextConstant;
import wechatocr.picture.text.service.ImageRecService;
import wechatocr.service.baidu.BaiDuOcrService;
import wechatocr.service.qiniu.QiNiuService;
import wechatocr.utils.ContentUtils;
import wechatocr.utils.JacksonUtils;
import wechatocr.utils.MyMessageUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/8/29 23:36
 * @Version 1.0
 */
@Service
public class ImageRecServiceImpl implements ImageRecService {
    private String respMsg = PictureTextConstant.SUCCESS;
    @Autowired
    private BaiDuOcrService baiDuOcrService;
    @Autowired
    private QiNiuService qiNiuService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MessageChannel output;

    @Override
    @SuppressWarnings("unchecked")
    public String textRecognition(Map<String, String> reqMap, String msgId, TextMessage textMessage) {
        String picUrl = reqMap.get("PicUrl");
        String response ;
        logger.info("微信端发送图片URL:" + picUrl);
        Boolean cache = redisTemplate.hasKey(msgId);
        if (Boolean.TRUE.equals(cache)) {
            logger.info("上一次请求超时,此次请求尝试不断从缓存中拿取数据");
            String cacheResponse = (String) redisTemplate.opsForValue().get(msgId);
            if (!PictureTextConstant.SUCCESS.equals(cacheResponse)) {
                respMsg = cacheResponse;
            } else {
                getCacheResultCas(msgId);
            }
            return respMsg;

        } else {
            try {
                redisTemplate.opsForValue().set(msgId, "success");
                logger.info("msgId:{} 存放至redis",msgId);
                response = baiDuOcrService.postAccurateBasic(picUrl);
                UserOcrHistory userOcrHistory = new UserOcrHistory();
                userOcrHistory.setMsgId(msgId);
                userOcrHistory.setOpenId(reqMap.get("FromUserName"));
                userOcrHistory.setMsgType(reqMap.get("MsgType"));
                userOcrHistory.setCreateTime(new Timestamp(System.currentTimeMillis()));
                if (!PictureTextConstant.SUCCESS.equals(response)) {
                    userOcrHistory.setContent(response);
                    // 汉字采用utf-8编码时占3个字节
                    int size = response.getBytes(StandardCharsets.UTF_8).length;
                    if (size > PictureTextConstant.WECHAT_MAX_WORDS) {
                        String fileName = reqMap.get("FromUserName") + System.currentTimeMillis() / 1000 + ".txt";
                        String filePath = ContentUtils.creatTempFile(fileName, response);
                        DefaultPutRet defaultPutRet = qiNiuService.fileUploadByPath(Zone.zone0(), qiNiuService.getUploadCredential(), filePath, fileName);
                        if (defaultPutRet == null) {
                            defaultPutRet = new DefaultPutRet();
                        }
                        String url = qiNiuService.fileUrl(defaultPutRet.key, QiNiuKey.DOMAIN_OF_BUCKET);
                        //上传到七牛云后删除本地文件节省内存
                        File temp = new File(filePath);
                        boolean delete = temp.delete();
                        if (!delete) {
                            logger.error("本地txt文件删除失败");
                        }
                        response = ContentUtils.subStringByByte(response);
                        response += "\n\n" + "文字内容过长,点击<a href='http://" + url + "'>链接</a>" + "查看完整内容";
                    }
                    textMessage.setContent(response);
                    respMsg = MyMessageUtils.textMessageToXML(textMessage);
                    sendToRocketMq(userOcrHistory);
                    logger.info("提取拼接文字内容结束");
                } else {
                    respMsg = response;
                }
                return respMsg;
            } catch (IOException e) {
                e.printStackTrace();
                return respMsg;
            } finally {
                redisTemplate.opsForValue().set(msgId, respMsg, 60 * 10, TimeUnit.SECONDS);
            }
        }
    }

    @Async
    public void sendToRocketMq(UserOcrHistory userOcrHistory) {
        try {
            output.send(MessageBuilder.withPayload(JacksonUtils.obj2json(userOcrHistory)).build());
            logger.info("识别结果发送到消息队列");
        } catch (Exception e) {
            logger.error("发送到消息队列时异常");
            e.printStackTrace();
        }
    }

    /**
     * 自旋锁的形式获取缓存内容。只在第二三次请求到来时有效，如果第二次仍没有值，则在第三次请求中返回。
     */
    @SuppressWarnings("unchecked")
    private void getCacheResultCas(String msgId) {
        String secondReq = msgId + "2";
        Instant start = Instant.now();
        Instant end = Instant.now();
        while (PictureTextConstant.SUCCESS.equals(redisTemplate.opsForValue().get(msgId)) && Duration.between(start, end).getSeconds() <= 4) {
            end = Instant.now();
        }
        String result = (String) redisTemplate.opsForValue().get(msgId);
        if (PictureTextConstant.SUCCESS.equals(result)) {
            redisTemplate.opsForValue().set(secondReq, "false");
        } else {
            respMsg = result;
            logger.warn("此次微信端请求在第二次重试时请求成功");
        }
        //判断这是第三次请求
        if (Boolean.TRUE.equals(redisTemplate.hasKey(secondReq))) {
            if (PictureTextConstant.SUCCESS.equals(result)) {
                logger.error("三次请求全部超时");
            } else {
                respMsg = result;
                logger.warn("此次微信端请求在第三次重试时才请求成功");
            }
        }
    }
}
