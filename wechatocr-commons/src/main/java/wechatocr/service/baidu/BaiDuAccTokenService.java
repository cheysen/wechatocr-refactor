package wechatocr.service.baidu;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wechatocr.constant.BaiDuKey;
import wechatocr.exception.BusinessException;

/**
 * @Author Cheysen
 * @Description 获取百度access_token
 * @Date 2019/8/28 11:45
 */
public interface BaiDuAccTokenService {
    String CLIENT_ID = BaiDuKey.AK;
    String CLIENT_SECRET = BaiDuKey.SK;
    Logger logger = LoggerFactory.getLogger(BaiDuAccTokenService.class);

    /** 获取返回数据中的access_token
     * @return
     * @throws BusinessException
     */
    String getAccToken() throws BusinessException;

    /** 获取整个返回数据
     * @param ak
     * @param sk
     * @return
     * @throws BusinessException
     */
    JSONObject getAccToken(String ak, String sk) throws BusinessException;
}
