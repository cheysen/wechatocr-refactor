package wechatocr.service.baidu.impl;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wechatocr.exception.BusinessException;
import wechatocr.service.baidu.BaiDuAccTokenService;
import wechatocr.service.baidu.BaiDuOcrService;
import wechatocr.utils.ContentUtils;
import wechatocr.utils.MyUrlUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/8/28 16:19
 * @Version 1.0
 */
@Service
public class BaiDuOcrServiceImpl implements BaiDuOcrService {
    private String apiUrl = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic?access_token=";
    @Autowired
    private BaiDuAccTokenService baiDuAccTokenService;

    @Override
    public String postAccurateBasic(String url) {
        logger.info("百度OCR--请求文字识别API开始");
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("image", MyUrlUtils.base64(url)));
        params.add(new BasicNameValuePair("detect_direction", "true"));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost();
        try {
            URI postUrl = new URI(apiUrl);
            httpPost.setURI(postUrl);
            ////设置请求头，请求头必须为application/x-www-form-urlencoded，因为是传递一个很长的字符串，不能分段发送
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String str = EntityUtils.toString(response.getEntity());
            logger.info("请求结束,返回状态码为:" + response.getStatusLine().getStatusCode());
            logger.info("请求结果:" + str);
            if (response.getStatusLine().getStatusCode() == SUCCESS_STATUS) {
                return ContentUtils.extract(str);
            }
        } catch (IOException | URISyntaxException e) {
            logger.error("请求百度文字识别时发生异常");
            e.printStackTrace();
            return "success";
        }
        return "success";
    }

    @Scheduled(fixedDelay = 2592000L*1000L)
    public void refreshAccessToken() throws BusinessException {
        String accessToken = baiDuAccTokenService.getAccToken();
        logger.info("获取百度access_token");
        apiUrl += accessToken;
    }
}
