package wechatocr.picture.text.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import wechatocr.domain.message.response.TextMessage;
import wechatocr.picture.text.service.ImageRecService;

import java.util.Map;

/**
 * @Author Cheysen
 * @Description 图片识别
 * @Date 2019/8/29 23:16
 * @Version 1.0
 */
@RestController
public class PictureController {

    @Autowired
    private ImageRecService imageRecService;
    @PostMapping(value = "/text")
    public String textInPicture(@RequestParam Map<String,String> reqMap, @RequestParam(value = "msgId") String msgId, @RequestBody TextMessage textMsg){
        return imageRecService.textRecognition(reqMap,msgId,textMsg);
    }
}
