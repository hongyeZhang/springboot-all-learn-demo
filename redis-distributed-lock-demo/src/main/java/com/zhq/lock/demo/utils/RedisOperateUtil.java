package com.zhq.lock.demo.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author : ZHQ
 * @date : 2020/3/29
 */
@Slf4j
@Component
public class RedisOperateUtil {

    @Autowired
    private JedisUtil jedisUtil;


    public void setKey(String key, String value) {
        Jedis jedis = jedisUtil.getJedis();

        try {
            // 设置成功，返回OK
            String set = jedis.set(key, value);
            System.out.println(set);
        } finally {
            jedis.close();
        }
    }


    public Long incByDelta(String key, long delta) {
        Jedis jedis = jedisUtil.getJedis();
        Long aLong;
        try {
            aLong = jedis.incrBy(key, delta);
            log.info("incByDelta output :" + aLong);
        } finally {
            jedis.close();
        }
        return aLong;
    }

    public String getKey(String key) {
        Jedis jedis = jedisUtil.getJedis();
        String ret;

        try {
            ret = jedis.get(key);
            log.info("getKey output : " + ret);
        } finally {
            jedis.close();
        }
        return ret;
    }








}
