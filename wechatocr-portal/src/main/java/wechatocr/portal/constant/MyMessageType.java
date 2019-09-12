package wechatocr.portal.constant;

/**
 * @Author Cheysen
 * @Description 消息类型(事件推送)枚举
 * @Date 2019/8/29 22:22
 * @Version 1.0
 */
public enum MyMessageType {
    /**
     * 图片类型消息
     */
    IMAGE("image"),
    /**
     * 文本消息类型
     */
    TEXT("text"),

    /**
     * 时间推送
     */
    EVENT("event"),
    /**
     * 关注事件
     */
    SUBSCRIBE("subscribe"),

    /**
     * 取关事件
     */
    UNSUBSCRIBE("unsubscribe");
    private final String name;
    MyMessageType(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
