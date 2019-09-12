package wechatocr.portal.controller;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wechatocr.domain.message.response.TextMessage;
import wechatocr.portal.constant.MyMessageType;
import wechatocr.portal.service.OcrHistoryService;
import wechatocr.portal.service.PicTextService;
import wechatocr.portal.service.SubscribeService;
import wechatocr.utils.CheckSignatureUtils;
import wechatocr.utils.CommonEmailUtils;
import wechatocr.utils.MyMessageUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/8/29 0:18
 * @Version 1.0
 */
@RestController
@RequestMapping("/wechat/picture")
public class WeChatController {
    private Logger logger = LoggerFactory.getLogger(WeChatController.class);
    private final String watermark = "目前自动去水印功能还不完善,您可以搜索小程序:花生软件水印喵\n" +
            "配合手动框选水印位置来去除水印";
    @Autowired
    private PicTextService picTextService;
    @Autowired
    private SubscribeService subscribeService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private OcrHistoryService historyService;

    @GetMapping
    public String get(HttpServletRequest request) throws NoSuchAlgorithmException {
        logger.info("检查微信认证请求");
        //微信加密签名,signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
        String signature = request.getParameter("signature");
        //时间戳
        String timestamp = request.getParameter("timestamp");
        //随机数
        String nonce = request.getParameter("nonce");
        //随机字符串
        String echostr = request.getParameter("echostr");
        logger.info("是否认证成功:" + CheckSignatureUtils.check(signature, timestamp, nonce));
        if (CheckSignatureUtils.check(signature, timestamp, nonce)) {
            logger.info("认证通过,已确认请求来自微信服务器");
            return echostr;
        } else {
            return null;
        }
    }
    @PostMapping
    @SuppressWarnings("unchecked")
    public String post(HttpServletRequest request) throws EmailException {
        String response = "success";
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.info("不支持的编码类型");
            return "success";
        }
        logger.debug("开始解析微信服务器端请求");
        //解析XML请求
        Map<String, String> reqMap = MyMessageUtils.praseXML(request);
        //发送方账号
        String fromUserName = reqMap.get("FromUserName");
        //公众账号
        String toUserName = reqMap.get("ToUserName");
        String msgId = reqMap.get("MsgId");
        //消息类型
        String msgType = reqMap.get("MsgType");
        if (MyMessageType.EVENT.getName().equals(msgType)) {
            msgType = reqMap.get("Event");
        }
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUserName);
        textMessage.setFromUserName(toUserName);
        textMessage.setCreateTime(System.currentTimeMillis() / 1000);
        textMessage.setMsgType(MyMessageType.TEXT.getName());
        switch (MyMessageType.valueOf(msgType.toUpperCase())) {
            case IMAGE:
                Boolean isOperationCode = redisTemplate.hasKey(fromUserName);
                if (Boolean.TRUE.equals(isOperationCode)) {
                    if ("1".equals(redisTemplate.opsForValue().get(fromUserName))) {
                        response = picTextService.textInPicture(reqMap, msgId, textMessage);
                        infoEmailToMe(response);
                    } else {
                        //自动图片去水印功能尚不完善
                        textMessage.setContent(watermark);
                        response = MyMessageUtils.textMessageToXML(textMessage);
                    }

                } else {
                    //如果没有输入操作码,则默认是文字识别
                    response = picTextService.textInPicture(reqMap, msgId, textMessage);
                    infoEmailToMe(response);
                }
                break;
            case TEXT:
                response = myDispatcher(reqMap.get("Content"), fromUserName, textMessage);
                break;
            case SUBSCRIBE:
                response = subscribeService.subscirbe(textMessage, fromUserName);
                break;
            case UNSUBSCRIBE:
                response = subscribeService.unsubscirbe(fromUserName);
            default:
                logger.info("收到用户的未定义处理消息,消息类型:{}",msgType);
                break;
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    private String myDispatcher(String content, String userName,TextMessage textMessage) {
        switch (content) {
            case "1":
                redisTemplate.opsForValue().set(userName, content);
                return "success";
            case "2":
                redisTemplate.opsForValue().set(userName, content);
                textMessage.setContent(watermark);
                return MyMessageUtils.textMessageToXML(textMessage);
            case "0":
                textMessage.setContent(historyService.getOcrHistory(userName));
                return MyMessageUtils.textMessageToXML(textMessage);
            default:
                logger.info("对已定义之外的文本消息不做处理");
                return "success";
        }
    }

    @Async
    public void infoEmailToMe(String res) throws EmailException {
        if ("success".equals(res)) {
            Email email = CommonEmailUtils.getCommonEmail();
            email.setSubject("图片识别接口调用返回了success");
            email.setFrom("chailin_csu@163.com");
            email.setMsg("请及时排查错误原因");
            email.addTo("1457516535@qq.com");
            email.send();
        }
    }

}
