package wechatocr.picture.text.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author Cheysen
 * @Description
 * @Date 2019/8/7 1:47
 * @Version 1.0
 */
@Configuration
public class RedisConfig {
    private RedisConnectionFactory redisConnectionFactory = null;

    @Bean(name = "RedisConnectionFactory")
    public RedisConnectionFactory initRedisConnectionFactory(){
        if(this.redisConnectionFactory !=null ){
            return this.redisConnectionFactory;
        }
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最大空闲数
        poolConfig.setMaxIdle(30);
        //最大连接数
        poolConfig.setMaxTotal(50);
        //最大等待毫秒数
        poolConfig.setMaxWaitMillis(2000);
        //获取单机的Redis配置
        RedisStandaloneConfiguration rsCfg = new RedisStandaloneConfiguration("49.234.157.247",6379);
        rsCfg.setPassword(RedisPassword.of("chen980816.."));
        //创建Jedis连接工厂
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(rsCfg, JedisClientConfiguration.builder().usePooling().poolConfig(poolConfig).build());
        redisConnectionFactory = connectionFactory;
        return redisConnectionFactory;
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<Object,Object> initRedisTemplate(){
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        RedisSerializer serializer = redisTemplate.getStringSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(serializer);
        redisTemplate.setConnectionFactory(initRedisConnectionFactory());
        return redisTemplate;
    }
}
