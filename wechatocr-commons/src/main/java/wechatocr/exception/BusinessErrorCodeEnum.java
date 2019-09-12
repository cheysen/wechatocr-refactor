package wechatocr.exception;

import org.apache.commons.lang3.StringUtils;

public enum BusinessErrorCodeEnum implements ErrorCode{
    /**
     * 百度获取access_token失败
     */
    INVALID_CLIENT("invalid_client","百度API Key或Secret Key不正确"),

    /**
     * 未指定的异常
     */
    UNSPECIFIED("undefined","未指定义异常");

    private final String code;
    private final String description;

    BusinessErrorCodeEnum(final String code,final String description){
        this.code = code;
        this.description = description;
    }
    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /** 根据编码查询枚举
     * @param code
     * @return
     */
    public static BusinessErrorCodeEnum getByCode(String code){
        for (BusinessErrorCodeEnum value: BusinessErrorCodeEnum.values()){
            if(StringUtils.equals(code, value.getCode())){
                return value;
            }
        }
        return UNSPECIFIED;
    }

    public static Boolean contains(String code){
        for (BusinessErrorCodeEnum value : BusinessErrorCodeEnum.values()){
            if(StringUtils.equals(code, value.getCode())){
                return true;
            }
        }
        return false;
    }
}
