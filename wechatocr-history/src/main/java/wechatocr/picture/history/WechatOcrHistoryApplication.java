package wechatocr.picture.history;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/9/8 16:15
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = "wechatocr")
@EnableDiscoveryClient
@MapperScan(basePackages ="wechatocr.mapper.mymapper")
@EnableBinding({Sink.class})
public class WechatOcrHistoryApplication{
    public static void main(String[] args) {
        SpringApplication.run(WechatOcrHistoryApplication.class,args);
    }
}
