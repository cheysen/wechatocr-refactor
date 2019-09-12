package wechatocr.commons.domain;

import java.util.Date;
import javax.persistence.*;

@Table(name = "user_ocr_history")
public class UserOcrHistory {
    /**
     * 消息id
     */
    @Id
    @Column(name = "msg_id")
    private String msgId;

    /**
     * 微信端传过来的用户id
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 消息类型
     */
    @Column(name = "msg_type")
    private String msgType;

    /**
     * 请求创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 识别结果
     */
    private String content;

    /**
     * 获取消息id
     *
     * @return msg_id - 消息id
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * 设置消息id
     *
     * @param msgId 消息id
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * 获取微信端传过来的用户id
     *
     * @return open_id - 微信端传过来的用户id
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置微信端传过来的用户id
     *
     * @param openId 微信端传过来的用户id
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取消息类型
     *
     * @return msg_type - 消息类型
     */
    public String getMsgType() {
        return msgType;
    }

    /**
     * 设置消息类型
     *
     * @param msgType 消息类型
     */
    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    /**
     * 获取请求创建时间
     *
     * @return create_time - 请求创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置请求创建时间
     *
     * @param createTime 请求创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取识别结果
     *
     * @return content - 识别结果
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置识别结果
     *
     * @param content 识别结果
     */
    public void setContent(String content) {
        this.content = content;
    }
}