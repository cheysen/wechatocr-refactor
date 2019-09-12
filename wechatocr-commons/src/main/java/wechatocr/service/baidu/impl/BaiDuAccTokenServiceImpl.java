package wechatocr.service.baidu.impl;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import wechatocr.exception.BusinessErrorCodeEnum;
import wechatocr.exception.BusinessException;
import wechatocr.service.baidu.BaiDuAccTokenService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/8/28 11:48
 * @Version 1.0
 */
@Service
public class BaiDuAccTokenServiceImpl implements BaiDuAccTokenService {
    @Override
    public String getAccToken() throws BusinessException {
        JSONObject response = getAccToken(CLIENT_ID, CLIENT_SECRET);
        if(response.has("access_token")){
            return response.getString("access_token");
        }else if(response.has("error")){
            throw new BusinessException(BusinessErrorCodeEnum.INVALID_CLIENT);
        }else {
            logger.error("获取百度access_token失败");
            throw new BusinessException(BusinessErrorCodeEnum.UNSPECIFIED);
        }
    }


    @Override
    public JSONObject getAccToken(String ak, String sk) throws BusinessException {
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                + "grant_type=client_credentials"
                + "&client_id=" + ak
                + "&client_secret=" + sk;
        int responseCode = 500;
        try {
            URL url = new URL(getAccessTokenUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            logger.info("成功建立获取百度access_token的连接");
            Map<String, List<String>> headerMap = connection.getHeaderFields();
            responseCode = connection.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result=new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            return new JSONObject(result.toString());
        } catch (Exception e) {
            logger.error("获取百度access_token出错 ,responseCode: {} ",responseCode);
            throw new BusinessException(e);
        }
    }
}
