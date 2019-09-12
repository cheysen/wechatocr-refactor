package wechatocr.exception;

/**
 * @Author Cheysen
 * @Description 错误码接口
 * @Date 2019/8/28 14:20
 */
public interface ErrorCode {
    /** 获取错误码
     * @return
     */
    String getCode();

    /** 获取错误信息
     * @return
     */
    String getDescription();
}
