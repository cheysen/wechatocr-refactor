package wechatocr.service.qiniu.impl;

import com.qiniu.common.Zone;
import com.qiniu.storage.model.DefaultPutRet;
import wechatocr.service.qiniu.QiNiuService;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/8/28 22:23
 * @Version 1.0
 */
public class QiNiuServiceImpl implements QiNiuService {
    @Override
    public DefaultPutRet fileUploadByByte(String key, String content) {
        return null;
    }

    @Override
    public DefaultPutRet fileUploadByPath(Zone zone, String upToken, String localFilePath, String key) {
        return null;
    }

    @Override
    public String getUploadCredential() {
        return null;
    }

    @Override
    public String fileUrl(String fileName, String domainOfBucket) {
        return null;
    }
}
