package com.test.zha.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.zha.redis.util.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @program: redisTestNew
 * @description: config
 * @author: ZHQ
 * @create: 2018-11-09 12:11
 **/

@Configuration
@EnableCaching
public class RedisConfig {

    @Autowired
    private DefaultRedisDataSourceInfo defaultRedis;

    @Autowired
    private SecondRedisDataSourceInfo secondRedis;


    public JedisConnectionFactory createJedisConnectionFactory(BasicRedisInfo basicRedisInfo) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setDatabase(basicRedisInfo.getDatabase());
        jedisConnectionFactory.setHostName(basicRedisInfo.getHost());
        jedisConnectionFactory.setPort(basicRedisInfo.getPort());
        if (StringUtils.isNotEmpty(basicRedisInfo.getPassword())) {
            jedisConnectionFactory.setPassword(basicRedisInfo.getPassword());
        }
        jedisConnectionFactory.setTimeout(basicRedisInfo.getTimeout());
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(basicRedisInfo.getPool().getMaxIdle());
        poolConfig.setMinIdle(basicRedisInfo.getPool().getMinIdle());
        poolConfig.setMaxTotal(basicRedisInfo.getPool().getMaxActive());
        poolConfig.setMaxWaitMillis(basicRedisInfo.getPool().getMaxWait());
        poolConfig.setTestOnBorrow(true);
        jedisConnectionFactory.setPoolConfig(poolConfig);
        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }


    @Bean(value = "redisTemplate")
    RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate(createJedisConnectionFactory(defaultRedis), getStringRedisSerializer(), getJackson2JsonRedisSerializer());
        RedisUtils.setRedisTemplate(redisTemplate);
        return redisTemplate;
    }

    @Bean(value = "redisTemplate2")
    RedisTemplate<String, Object> redisTemplate2() {
        RedisTemplate<String, Object> redisTemplate2 = getRedisTemplate2(createJedisConnectionFactory(secondRedis), getStringRedisSerializer(), getJackson2JsonRedisSerializer());
        RedisUtils.setRedisTemplate2(redisTemplate2);
        return redisTemplate2;
    }

    StringRedisSerializer getStringRedisSerializer() {
        return new StringRedisSerializer();
    }

    Jackson2JsonRedisSerializer<Object> getJackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        return jackson2JsonRedisSerializer;
    }


    public RedisTemplate<String, Object> getRedisTemplate(RedisConnectionFactory factory, StringRedisSerializer stringRedisSerializer, Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate2(RedisConnectionFactory factory, StringRedisSerializer stringRedisSerializer, Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }


    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate);
        return redisCacheManager;
    }
}
