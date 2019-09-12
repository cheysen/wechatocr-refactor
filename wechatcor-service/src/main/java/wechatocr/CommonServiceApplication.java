package wechatocr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/8/28 17:09
 * @Version 1.0
 */
@SpringBootApplication
@EnableScheduling
public class CommonServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonServiceApplication.class,args);
    }
}
