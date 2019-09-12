package wechatocr.exception;

/**
 * @Author Cheysen
 * @Description 自定义异常类
 * @Date 2019/8/28 14:04
 * @Version 1.0
 */
public class BusinessException extends Exception{
    private static final long serialVersionUID = -7864604160297181941L;
    protected final ErrorCode errorCode;
    private String code;

    /** 无参默认异常
     *
     */
    public BusinessException(){
        super(BusinessErrorCodeEnum.UNSPECIFIED.getDescription());
        this.errorCode = BusinessErrorCodeEnum.UNSPECIFIED;
    }

    /** 指定错误码构造通用异常
     * @param errorCode
     */
    public BusinessException(final ErrorCode errorCode){
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    /** 指定详细描述构造通用异常
     * @param detailMessage
     */
    public BusinessException(final String detailMessage){
        super(detailMessage);
        this.errorCode = BusinessErrorCodeEnum.UNSPECIFIED;
    }

    /** 指定异常超类构造通用异常
     * @param cause
     */
    public BusinessException(final Throwable cause){
        super(cause);
        this.errorCode = BusinessErrorCodeEnum.UNSPECIFIED;
    }

    /** 构造通用异常
     * @param errorCode
     * @param detailMessage
     */
    public BusinessException(final ErrorCode errorCode, final String detailMessage){
        super(detailMessage);
        this.errorCode = errorCode;
    }

    /** 构造通用异常
     * @param errorCode
     * @param detailMessage
     * @param cause
     */
    public BusinessException(final ErrorCode errorCode, final String detailMessage, final Throwable cause){
        super(detailMessage, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }

}
