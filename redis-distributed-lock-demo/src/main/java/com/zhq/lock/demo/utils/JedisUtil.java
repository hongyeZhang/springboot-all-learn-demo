package com.zhq.lock.demo.utils;



import com.zhq.lock.demo.config.RedisProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 * @author ZHQ
 */
@Component
public class JedisUtil {
    @Autowired
    private RedisProperties redisProperties;

    private Logger logger = LoggerFactory.getLogger(JedisUtil.class);

    private  Map<String, JedisPool> map = new ConcurrentHashMap<>();

    private JedisPool getPool() {
        String key = redisProperties.getHost() + ":" + redisProperties.getHost();
        JedisPool pool;
        if (!map.containsKey(key)) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxIdle(redisProperties.getMax_idle());
            config.setMaxWaitMillis(redisProperties.getMax_wait());
            config.setTestOnBorrow(true);
            config.setTestOnReturn(true);

            pool = new JedisPool(config,redisProperties.getHost(),redisProperties.getPort(),redisProperties.getTimeout());
            map.put(key, pool);
        } else {
            pool = map.get(key);
        }
        return pool;
    }

    public Jedis getJedis() {
        Jedis jedis = null;
        int count = 0;
        do {
            try {
                jedis = getPool().getResource();
                count++;
            } catch (Exception e) {
                logger.error("get jedis failed ", e);
                if (jedis != null) {
                    jedis.close();
                }
            }
        } while (jedis == null && count < redisProperties.getRetry_num());
        return jedis;
    }
}
