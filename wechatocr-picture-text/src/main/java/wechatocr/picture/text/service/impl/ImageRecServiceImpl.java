package wechatocr.picture.text.service.impl;

import com.qiniu.common.Zone;
import com.qiniu.storage.model.DefaultPutRet;
import org.springframework.beans.factory.annotation.Autowired;
import wechatocr.constant.QiNiuKey;
import wechatocr.message.response.TextMessage;
import wechatocr.picture.text.constant.PictureTextConstant;
import wechatocr.picture.text.service.ImageRecService;
import wechatocr.service.baidu.BaiDuOcrService;
import wechatocr.service.qiniu.QiNiuService;
import wechatocr.utils.ContentUtils;
import wechatocr.utils.MyMessageUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/8/29 23:36
 * @Version 1.0
 */
public class ImageRecServiceImpl implements ImageRecService {
    private String respMsg = PictureTextConstant.SUCCESS;
    @Autowired
    private BaiDuOcrService baiDuOcrService;
    @Autowired
    private QiNiuService qiNiuService;
    @Override
    public void textRecognition(Map<String, String> reqMap, String msgId, TextMessage textMessage) {
        String picUrl = reqMap.get("PicUrl");
        String response = "";
        logger.info("微信端发送图片URL为:" + picUrl);
        /*HashMap<String,String> cacheMap = (HashMap<String, String>) ImageToTextCache.getInstance().getCache();
        if (cacheMap.containsKey(msgId)) {
            logger.info("上一次请求超时,此次请求从缓存中拿取数据不再请求百度OCR的API");
            response = cacheMap.get(msgId);
            if(!SUCCESS.equals(response)) {
                textMessage.setContent(response);
                respMsg = MessageUtils.textMessageToXML(textMessage);
            }else {
                respMsg = response;
            }
        }*/ else {
            try {
                response = baiDuOcrService.postAccurateBasic(picUrl);
                if (!PictureTextConstant.SUCCESS.equals(response)) {
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
                    logger.info("提取拼接文字内容结束");
                } else {
                    respMsg = response;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {

            }
        }
    }
}
