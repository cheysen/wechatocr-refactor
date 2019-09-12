package wechatocr.picture.text.controller;


import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import wechatocr.domain.message.response.TextMessage;
import wechatocr.picture.text.constant.PictureTextConstant;
import wechatocr.utils.CommonEmailUtils;
import wechatocr.utils.MyMessageUtils;


/**
 * @Author Cheysen
 * @Description 获取用户行为
 * @Date 2019/9/2 2:00
 * @Version 1.0
 */
@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @PostMapping(value = "/subscribe")
    String subscirbe(@RequestBody TextMessage textMsg, @RequestParam(value = "fromUserName") String fromUserName) throws EmailException {
        String subMsg = "新增关注用户:" + fromUserName;
        logger.info(subMsg);
        textMsg.setContent("欢迎使用图片识别小助手(目前只支持通用的文字识别)使用说明:\n输入1再发送图片:通用文字识别\n" +
                "输入2:图片去水印\n" +
                "输入0:查询文字识别历史记录\n" +
                "系统会记住上一次所选择的功能标号,如需切换只需再次发送功能编号即可。历史记录只能查询最近50条");
        emailService(subMsg);
        return MyMessageUtils.textMessageToXML(textMsg);
    }

    /** 取关事件服务接口
     * @param fromUserName 用户名
     * @return
     */
    @GetMapping(value = "/unsubscribe")
    String unsubscirbe(@RequestParam(value = "fromUserName") String fromUserName) throws EmailException {
        String unSubMsg = "用户" + fromUserName + "取消关注";
        logger.info(unSubMsg);
        emailService(unSubMsg);
        return PictureTextConstant.SUCCESS;
    }
    @Async
    public void emailService(String msg) throws EmailException {
        Email email = CommonEmailUtils.getCommonEmail();
        email.setSubject(msg);
        email.setFrom("chailin_csu@163.com");
        email.setMsg("写到世界充满爱,冲啊!");
        email.addTo("1457516535@qq.com");
        email.send();
    }
}
