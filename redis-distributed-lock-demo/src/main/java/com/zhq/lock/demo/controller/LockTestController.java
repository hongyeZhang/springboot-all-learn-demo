package com.zhq.lock.demo.controller;


import com.zhq.lock.demo.utils.JedisUtil;
import com.zhq.lock.demo.utils.RedisLockHelper;
import com.zhq.lock.demo.utils.RedisOperateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

/**
 * @author : ZHQ
 * @date : 2020/3/29
 */
@Slf4j
@RestController
public class LockTestController {

    public static final int MAX = 5;

    public static final String LOCK_LIMIT = "lock-limit";

    @Autowired
    RedisOperateUtil redisOperateUtil;

    @Autowired
    private JedisUtil jedisUtil;


    @Autowired
    private RedisLockHelper redisLockHelper;


    @Autowired
    ExecutorService lockExecutorService;


    @RequestMapping(value = "/lock/distributed/test")
    public String testDistributedLock() {

        for (int i = 0; i < 30; ++i) {
            lockExecutorService.execute(()->{
                doTest();
            });
        }
        return "success";
    }



    public void doTest() {
        String lockKey = "userid-activityid";
        Jedis jedis = jedisUtil.getJedis();

        String lockValue = UUID.randomUUID().toString();
        int expire = 300;
        int timewait = 1000;

        try {
            final boolean islock = redisLockHelper.lockWithWaitTimeMill(jedis, lockKey, lockValue, expire, timewait);
            if (islock) {
                if (Long.parseLong(redisOperateUtil.getKey(LOCK_LIMIT)) < 5) {
                    redisOperateUtil.incByDelta(LOCK_LIMIT, 1);
                    redisOperateUtil.setKey(Thread.currentThread().getName(), lockValue);
                } else {
                    log.error("max limit ");
                }
            } else {
                log.warn("acquire distributed lock failed");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("release distributed lock");
            redisLockHelper.unlock(jedis, lockKey, lockValue);
            jedis.close();
        }
    }


}
