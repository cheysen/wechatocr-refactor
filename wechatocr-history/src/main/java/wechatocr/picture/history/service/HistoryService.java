package wechatocr.picture.history.service;

import com.qiniu.common.Zone;
import com.qiniu.storage.model.DefaultPutRet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import wechatocr.constant.QiNiuKey;

import wechatocr.domain.data.object.UserOcrHistory;
import wechatocr.mapper.mymapper.UserOcrHistoryMapper;
import wechatocr.service.qiniu.QiNiuService;
import wechatocr.utils.ContentUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/9/8 20:46
 * @Version 1.0
 */
@Service
public class HistoryService {
    private final int historyMaxNumber =  50;
    private final int maxWords = 2047;
    private Logger logger = LoggerFactory.getLogger(RocketMqService.class);
    @Autowired
    private UserOcrHistoryMapper userOcrHistoryMapper;
    @Autowired
    private QiNiuService qiNiuService;
    public String getHistory(String fromUserName) {
        String response="success";
        UserOcrHistory ocrHistory = new UserOcrHistory();
        ocrHistory.setOpenId(fromUserName);
        Example  example = new Example(UserOcrHistory.class);
        example.createCriteria().andEqualTo("openId",ocrHistory.getOpenId());
        example.orderBy("createTime").desc();
        List<UserOcrHistory> historyList = userOcrHistoryMapper.selectByExample(example);
        StringBuilder resultHistory = new StringBuilder("最近50条历史记录如下\n\n");
        int listSize = historyList.size();
        if (listSize > 0 ) {
            if(listSize >= historyMaxNumber){
                listSize = historyMaxNumber;
            }
            for (int i = 0; i < listSize; i++) {
                resultHistory.append("###").append(i).append("###\n")
                        .append(historyList.get(i).getContent()).append("\n");
            }
            response = resultHistory.toString();
        }
        int size = resultHistory.toString().getBytes(StandardCharsets.UTF_8).length;
        if(size > maxWords){
            String fileName = "history" + System.currentTimeMillis() / 1000 + ".txt";
            String filePath = null;
            try {
                filePath = ContentUtils.creatTempFile(fileName, resultHistory.toString());
            } catch (IOException e) {
                logger.error("创建临时文件出错");
                e.printStackTrace();
            }
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
            response = ContentUtils.subStringByByte(resultHistory.toString());
            response += "\n\n" + "内容过长,点击<a href='http://" + url + "'>链接</a>" + "查看完整内容";
        }
        return response;
    }
}
