package wechatocr.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.annotation.PostConstruct;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/8/29 0:04
 * @Version 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
public class WeChatOcrPortalApplication {
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
    }
    public static void main(String[] args) {
        SpringApplication.run(WeChatOcrPortalApplication.class, args);
    }
}
