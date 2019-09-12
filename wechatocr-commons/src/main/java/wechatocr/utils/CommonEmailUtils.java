package wechatocr.utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

/**
 * @Author Cheysen
 * @Description 获得邮件对象
 * @Date 2019/9/2 2:55
 * @Version 1.0
 */
public class CommonEmailUtils {
    public static Email getCommonEmail(){
        Email email = new SimpleEmail();
        email.setHostName("smtp.163.com");
        email.setSmtpPort(465);
        email.setAuthenticator(new DefaultAuthenticator("chailin_csu@163.com", "HJ980816"));
        email.setSSLOnConnect(true);
        email.setCharset("UTF-8");
        return email;
    }
}
