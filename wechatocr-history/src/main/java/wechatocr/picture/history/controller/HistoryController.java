package wechatocr.picture.history.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wechatocr.picture.history.service.HistoryService;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/9/8 23:31
 * @Version 1.0
 */
@RestController
public class HistoryController {
    @Autowired
    private HistoryService historyService;
    @GetMapping("/history/{openId}")
    public String getOcrHistory(@PathVariable String openId){
        return historyService.getHistory(openId);
    }
}
