package wechatocr.service.qiniu.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;
import wechatocr.constant.QiNiuKey;
import wechatocr.service.qiniu.QiNiuService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/8/28 22:23
 * @Version 1.0
 */
@Service
public class QiNiuServiceImpl implements QiNiuService {
    @Override
    public DefaultPutRet fileUploadByByte(String key, String content) {
        //构造一个带指定Zone对象的配置类,这里是华东
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        byte[] uploadBytes = content.getBytes(StandardCharsets.UTF_8);
        String upToken = getUploadCredential();
        try {
            Response response = uploadManager.put(uploadBytes, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet;
        } catch (QiniuException ex) {
            Response r = ex.response;
            logger.error(r.toString());
            System.err.println(r.toString());
            try {
                logger.error(r.bodyString());
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    @Override
    public DefaultPutRet fileUploadByPath(Zone zone, String upToken, String localFilePath, String key) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(zone);
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            // 解析上传成功的结果
            return new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            Response r = ex.response;
            logger.error(r.toString());
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
                logger.error(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
        }
        return null;
    }

    @Override
    public String getUploadCredential() {
        Auth auth = Auth.create(QiNiuKey.QINIU_AK,QiNiuKey.QINIU_SK);
        return auth.uploadToken(QiNiuKey.QINUI_BUCKET);
    }

    @Override
    public String fileUrl(String fileName, String domainOfBucket) {
        String fileNameEncode = null;
        try{
            fileNameEncode = URLEncoder.encode(fileName,"UTF-8");
        }catch (UnsupportedEncodingException e) {
            logger.error("上传文件到七牛:文件名命名不支持的编码类型");
        }
        return String.format("%s/%s",domainOfBucket,fileNameEncode);
    }
}
