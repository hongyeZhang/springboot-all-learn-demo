//-------------------------------------------------------------------------------------
//CMB O2O Confidential
//
//Copyright (C) 2016 China Merchants Bank Co., Ltd. O2O All rights reserved.
//
//No part of this file may be reproduced or transmitted in any form or by any means,
//electronic, mechanical, photocopying, recording, or otherwise, without prior
//written permission of China Merchants Bank Co., Ltd. O2O
//
//-------------------------------------------------------------------------------------
package com.zhq.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.MultiKeyCommands;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;


/**
 * @author ZHQ
 */
@Service
@Slf4j
public class RedisUtils {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtils.class);

    @Autowired
    private RedisTemplate redisTemplate;


    public static final String UNLOCK_LUA;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }


    public Map<String, Integer> entryMap(final String group) {
        HashOperations<String, String, Integer> hash = redisTemplate.opsForHash();
        return hash.entries(group);
    }

    public boolean exists(final String key) {
        logger.info("redisTemplate:{}, key:{}", redisTemplate, key);
        return redisTemplate.hasKey(key);
    }

    public boolean existMapValue(final String group, final String key) {
        HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
        return hash.hasKey(group, key);
    }

    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    public Object get(final String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    public Map<String, Object> getMap(final String group) {
        HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
        return hash.entries(group);
    }

    public Object getMapValue(final String group, final String key) {
        try {
            HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
            return hash.get(group, key);
        } catch (Exception e) {
        }

        return null;
    }

    public Long incMapValue(String group, String key, Long value) {
        HashOperations<String, String, Long> operations = redisTemplate.opsForHash();
        return operations.increment(group, key, value);
    }

    public void setIncMapValue(String group, String key, Integer value) {
        HashOperations<String, String, Integer> operations = redisTemplate.opsForHash();
        operations.put(group, key, value);
    }

    public void setIncMapValue(String group, String key, Long value) {
        HashOperations<String, String, Long> operations = redisTemplate.opsForHash();
        operations.put(group, key, value);
    }

    public Integer getIncMapValue(String group, String key) {
        HashOperations<String, String, Integer> operations = redisTemplate.opsForHash();
        return operations.get(group, key);
    }

    public boolean setMap(String group, String key, Object value) {
        boolean result = false;
        try {
            HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
            operations.put(group, key, value);
            result = true;
        } catch (Exception e) {
        }

        return result;
    }

    public boolean setMap(String group, String key, Object value, Long expireTime,
                    TimeUnit timeUnit) {
        boolean result = false;
        try {
            HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
            operations.put(group, key, value);
            redisTemplate.boundHashOps(group).expire(expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
        }

        return result;
    }

    public void deleteMap(String group, String key) {
        try {
            HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
            operations.delete(group, key);
        } catch (Exception e) {
        }
    }

    public long setList(String key, List value) {
        try {
            // 删除key
            deleteValue(key);
            // 重置
            return addList(key, value);
        } catch (Exception e) {
        }
        return -1;
    }

    public long addList(String key, Object value) {
        try {
            ListOperations<String, Object> operations = redisTemplate.opsForList();
            return operations.rightPush(key, value);
        } catch (Exception e) {
        }
        return -1;
    }

    public long addList(String key, List values) {
        try {
            ListOperations<String, Object> operations = redisTemplate.opsForList();
            return operations.rightPushAll(key, values);
        } catch (Exception e) {
        }
        return -1;
    }

    public <T> List<T> getList(String key, long begin, long end) {
        try {
            ListOperations<String, T> operations = redisTemplate.opsForList();
            return operations.range(key, begin, end);
        } catch (Exception e) {
        }
        return null;
    }

    public <T> List<T> getList(String key) {
        try {
            ListOperations<String, T> operations = redisTemplate.opsForList();
            Long end = operations.size(key);
            return operations.range(key, 0, (end - 1));
        } catch (Exception e) {
        }
        return null;
    }

    public <T> List<T> getList(String key, int size) {
        try {
            ListOperations<String, T> operations = redisTemplate.opsForList();
            return operations.range(key, 0, size - 1);
        } catch (Exception e) {
        }
        return null;
    }

    public Set<String> getSet(String key) {
        try {
            SetOperations<String, String> operations = redisTemplate.opsForSet();
            return operations.members(key);
        } catch (Exception e) {
        }

        return null;
    }

    public long setSet(String key, String value) {
        try {
            SetOperations<String, String> operations = redisTemplate.opsForSet();
            return operations.add(key, value);
        } catch (Exception e) {
            logger.error("[Set] add value[{}] to key[{}] error.", value, key, e);
        }

        return -1;
    }

    public long setSetValues(String key, String... value) {
        try {
            SetOperations<String, String> operations = redisTemplate.opsForSet();
            return operations.add(key, value);
        } catch (Exception e) {
        }

        return -1;
    }

    public Cursor<String> scanSet(String key) {
        try {
            SetOperations<String, String> operations = redisTemplate.opsForSet();
            return operations.scan(key, ScanOptions.NONE);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * 移除集合中的一个或多个成员元素，不存在的成员元素会被忽略
     *
     * @param key
     * @param value
     * @return
     */
    public long removeSetValue(String key, String... value) {
        try {
            SetOperations<String, String> operations = redisTemplate.opsForSet();
            return operations.remove(key, value);
        } catch (Exception e) {
            logger.error("[Set] remove value[{}] from key[{}] error.", value, key, e);
        }
        return -1;
    }

    /**
     * 返回集合中元素的数量
     *
     * @param key
     * @return
     */
    public long sizeSet(String key) {
        try {
            SetOperations<String, String> operations = redisTemplate.opsForSet();
            return operations.size(key);
        } catch (Exception e) {
            logger.error("[Set] get key[{}] size error.", key, e);
        }
        return -1;
    }

    /**
     * 将指定成员 member 元素从 source 集合移动到 destination 集合 如果 source 集合不存在或不包含指定的 member 元素，则 不执行任何操作。否则，
     * member 元素从 source 集合中被移除，并添加到 destination 集合中去。 当 destination 集合已经包含 member 元素时， 只是简单地将
     * source 集合中的 member 元素删除。 当 source 或 destination 不是集合类型时，返回一个错误。
     *
     * @param source
     * @param value
     * @param destination
     * @return
     */
    public Boolean moveSet(String source, String value, String destination) {
        try {
            SetOperations<String, String> operations = redisTemplate.opsForSet();
            return operations.move(source, value, destination);
        } catch (Exception e) {
            logger.error("[Set] move value[{}] from source key[{}] to destination key[{}] error.",
                            value, source, destination, e);
        }
        return false;
    }

    public Object getValue(final String key) {
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            return operations.get(key);
        } catch (Exception e) {
        }
        return null;
    }

    public boolean setValue(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
        }
        return result;
    }

    public boolean incValueByDelta(String key, Long delta) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            Long increment = operations.increment(key, delta);
            log.info("incValueByDelta result :" + increment);
            result = true;
        } catch (Exception e) {
        }
        return result;
    }




    public boolean setValue(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
        }
        return result;
    }

    public boolean setIfAbsentValue(final String key, Object value, Long expireTime,
                    TimeUnit unit) {
        boolean result = true; // true 代表设置成功，之前不存在  false 代表设置失败，之前已存在
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            result = operations.setIfAbsent(key, value);
            // true 才设置超时
            if (result) {
                redisTemplate.expire(key, expireTime, unit);
            }
        } catch (Exception e) {
        }
        return result;
    }

    public boolean setIfAbsentExpireValue(final String key, Object value, Long expireTime,
                    TimeUnit unit) {
        boolean result = true; // true 代表设置成功，之前不存在  false 代表设置失败，之前已存在
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            result = operations.setIfAbsent(key, value);

            // true 才设置超时
            //            if (result) {
            redisTemplate.expire(key, expireTime, unit);
            //            }
        } catch (Exception e) {
        }
        return result;
    }

    public void deleteValue(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
        }
    }

    /**
     * @param keyPattern
     * @Author: ZHQ
     * @Description: 分页删除指定模式的key
     * @return: void
     * @Date: 19:05 2019/9/26
     */
    public void deleteKeysByPageGivenKeyPattern(String keyPattern) {
        deleteKeysByPageGivenKeyPattern(keyPattern, 5000);
    }

    /**
     * @param keyPattern
     * @param pageSize
     * @Author: ZHQ
     * @Description: 分页删除指定模式的key
     * @return: void
     * @Date: 19:03 2019/9/26
     */
    public void deleteKeysByPageGivenKeyPattern(String keyPattern, int pageSize) {
        redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            JedisCommands commands = (JedisCommands) connection.getNativeConnection();
            MultiKeyCommands multiKeyCommands = (MultiKeyCommands) commands;
            ScanParams scanParams = new ScanParams();
            scanParams.match(keyPattern);
            scanParams.count(pageSize);
            ScanResult<String> scan = multiKeyCommands.scan("0", scanParams);
            while (null != scan.getStringCursor()) {
                List<String> tmpList = scan.getResult();
                if (tmpList.size() > 0) {
                    String[] tmpArr = tmpList.toArray(new String[tmpList.size()]);
                    logger.info("delete key from redis, size = " + tmpArr.length);
                    multiKeyCommands.del(tmpArr);
                }
                if (!StringUtils.equals("0", scan.getStringCursor())) {
                    scan = multiKeyCommands.scan(scan.getStringCursor(), scanParams);
                    continue;
                } else {
                    break;
                }
            }
            return null;
        });
    }


    /** 获取分布式锁
     * @param key
     * @param value
     * @param exptime
     * @return
     */
    public  boolean setIfAbsentWithExpire(final String key, final String value,
                    final long exptime) {
        Boolean result = (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
                Object obj = connection.execute("set",
                                stringRedisSerializer.serialize(key),
                                stringRedisSerializer.serialize(value),
                                "NX".getBytes(StandardCharsets.UTF_8),
                                // 秒
                                "EX".getBytes(StandardCharsets.UTF_8),
                                String.valueOf(exptime).getBytes(StandardCharsets.UTF_8));
                return obj != null;
            }
        });
        return result;
    }

    /** 获取分布式锁（带超时）
     * @param key
     * @param value
     * @param timeout
     * @param waitTime
     * @return
     * @throws InterruptedException
     */
    public boolean lockWithWaitTimeMill(String key, String value, int timeout, long waitTime) throws InterruptedException {
        long sleepTime = 100;
        while (waitTime >= 0) {
            boolean ret = setIfAbsentWithExpire(key, value, timeout);
            if (ret) {
                return true;
            }
            waitTime -= sleepTime;
            Thread.sleep(sleepTime);
        }
        return false;
    }

    /** 释放锁
     * @param key
     * @param value
     * @return
     */
    public boolean releaseLock(String key,String value) {
        try {
            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            RedisCallback<Long> callback = (connection) -> {
                Object nativeConnection = connection.getNativeConnection();
                if (nativeConnection instanceof Jedis) {
                    // 单机模式
                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, Collections.singletonList(key), Collections.singletonList(value));
                }
                return 0L;
            };
            Long result = (Long) redisTemplate.execute(callback);
            log.info("release distributed lock : {} result : {} ", value, result);
            return result > 0;
        } catch (Exception e) {
            logger.error("release distributed lock exception", e);
        }

        return false;
    }

    public void deleteKeys(Set<String> keys) {
        redisTemplate.delete(keys);
    }

}
