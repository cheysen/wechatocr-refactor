package wechatocr.picture.text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/8/28 23:58
 * @Version 1.0
 */
@SpringBootApplication(scanBasePackages = "wechatocr")
@EnableDiscoveryClient
@EnableAsync
@EnableScheduling
@EnableBinding({Source.class})
public class WeChatOcrPicTextApplication {
    @Autowired
    private RedisTemplate redisTemplate;
    @PostConstruct
    public void init(){
        initRedisTemplate();
    }
    @SuppressWarnings("unchecked")
    private void initRedisTemplate(){
        RedisSerializer stringSerializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
    }
    public static void main(String[] args) {
        SpringApplication.run(WeChatOcrPicTextApplication.class, args);
    }
}
