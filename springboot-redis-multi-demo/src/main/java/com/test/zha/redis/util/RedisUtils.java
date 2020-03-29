
package com.test.zha.redis.util;

import org.springframework.data.redis.core.*;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class RedisUtils {

    private static RedisTemplate redisTemplate;

    private static RedisTemplate redisTemplate2;

    public static Map<String, Integer> entryMap(final String group) {
        HashOperations<String, String, Integer> hash = redisTemplate.opsForHash();
        return hash.entries(group);
    }

    public static  boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    public static boolean existMapValue(final String group, final String key) {
        HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
        return hash.hasKey(group, key);
    }

        public static void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    public static void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    public static void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    public static Object get(final String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    public static Map<String, Object> getMap(final String group) {
        HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
        return hash.entries(group);
    }

    public static Object getMapValue(final String group, final String key) {
        try {
            HashOperations<String, String, Object> hash = redisTemplate.opsForHash();
            return hash.get(group, key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Long incMapValue(String group, String key, Long value) {
        HashOperations<String, String, Long> operations = redisTemplate.opsForHash();
        return operations.increment(group, key, value);
    }

    public static void setIncMapValue(String group, String key, Integer value) {
        HashOperations<String, String, Integer> operations = redisTemplate.opsForHash();
        operations.put(group, key, value);
    }

    public static Integer getIncMapValue(String group, String key) {
        HashOperations<String, String, Integer> operations = redisTemplate.opsForHash();
        return operations.get(group, key);
    }

    public static boolean setMap(String group, String key, Object value) {
        boolean result = false;
        try {
            HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
            operations.put(group, key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean setMap(String group, String key, Object value, Long expireTime, TimeUnit timeUnit) {
        boolean result = false;
        try {
            HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
            operations.put(group, key, value);
            redisTemplate.boundHashOps(group).expire(expireTime, timeUnit);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void deleteMap(String group, String key) {
        try {
            HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
            operations.delete(group, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Set<String> getSet(String key) {
        try {
            SetOperations<String, String> operations = redisTemplate.opsForSet();
            return operations.members(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static long setSet(String key, String value) {
        try {
            SetOperations<String, String> operations = redisTemplate.opsForSet();
            return operations.add(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static long setSetValues(String key, String... value) {
        try {
            SetOperations<String, String> operations = redisTemplate.opsForSet();
            return operations.add(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static Cursor<String> scanSet(String key) {
        try {
            SetOperations<String, String> operations = redisTemplate.opsForSet();
            return operations.scan(key, ScanOptions.NONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object getValue(final String key) {
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            return operations.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object getValue2(final String key) {
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate2.opsForValue();
            return operations.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean setValue(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean setValue2(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate2.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean setValue(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean setIfAbsentValue(final String key, Object value, Long expireTime, TimeUnit unit) {
        boolean result = true; // true 代表设置成功，之前不存在  false 代表设置失败，之前已存在
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            result = operations.setIfAbsent(key, value);
            // true 才设置超时
            if (result) {
                redisTemplate.expire(key, expireTime, unit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean setIfAbsentExpireValue(final String key, Object value, Long expireTime, TimeUnit unit) {
        boolean result = true; // true 代表设置成功，之前不存在  false 代表设置失败，之前已存在
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            result = operations.setIfAbsent(key, value);

            // true 才设置超时
//            if (result) {
                redisTemplate.expire(key, expireTime, unit);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void deleteValue(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteValue2(String key) {
        try {
            redisTemplate2.delete(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Set<String> allKeys() {
        return (Set<String>) redisTemplate.keys("*");
    }


    public static RedisTemplate getRedisTemplate() {
        return RedisUtils.redisTemplate;
    }

    public static void setRedisTemplate(RedisTemplate redisTemplate){
        RedisUtils.redisTemplate = redisTemplate;
    }

    public static RedisTemplate getRedisTemplate2() {
        return RedisUtils.redisTemplate2;
    }

    public static void setRedisTemplate2(RedisTemplate redisTemplate){
        RedisUtils.redisTemplate2 = redisTemplate;
    }
}
