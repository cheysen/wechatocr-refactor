package wechatocr.service.qiniu;

import com.qiniu.common.Zone;
import com.qiniu.storage.model.DefaultPutRet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author Cheysen
 * @Description 七牛文件上传服务
 * @Date 2019/8/28 22:15
 */
public interface QiNiuService {
    Logger logger = LoggerFactory.getLogger(QiNiuService.class);

    /** 字节数组上传
     * @param key 文件名,默认不指定key的情况下，以文件内容的hash值作为文件名
     * @param content 上传的文本内容
     * @return com.qiniu.storage.model.DefaultPutRet
     */
    DefaultPutRet fileUploadByByte(String key, String content);

    /** 本地文件上传
     * @param zone 存储空间区
     * @param upToken 上传凭证
     * @param localFilePath 文件路径
     * @param key 七牛云文件名
     * @return com.qiniu.storage.model.DefaultPutRet
     */
    DefaultPutRet fileUploadByPath(Zone zone, String upToken, String localFilePath, String key);

    /** 获取七牛上传凭证
     * @return java.lang.String
     */
    String getUploadCredential();

    /** 获取公有空间文本链接
     * @param fileName 文件名
     * @param domainOfBucket 存储域
     * @return
     */
    String fileUrl(String fileName, String domainOfBucket);
}
