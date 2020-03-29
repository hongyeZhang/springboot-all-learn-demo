package com.zhq.controller;

import com.zhq.service.RedisUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : ZHQ
 * @date : 2020/3/29
 */
@Slf4j
@RestController
public class LockTestController {

    public static final Integer MAX = 5;

    public static final String LOCK_LIMIT = "lock-limit";

    @Autowired
    ExecutorService lockExecutorService;

    @Autowired
    RedisUtils redisUtils;


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
        String lockValue = UUID.randomUUID().toString();
        int expire = 300;
        int timewait = 1000;

        try {
            if (redisUtils.lockWithWaitTimeMill(lockKey, lockValue, expire, timewait)) {
                if ((Integer)redisUtils.getValue(LOCK_LIMIT) < MAX) {
                    redisUtils.incValueByDelta(LOCK_LIMIT, 1L);
                    redisUtils.setValue(Thread.currentThread().getName(), lockValue);
                } else {
                    log.warn("max limit");
                }
            } else {
                log.warn("acquire distributed lock failed");
            }
        } catch (InterruptedException e) {
            // just ignore
        } finally {
            log.info("release distributed lock");
            redisUtils.releaseLock(lockKey, lockValue);
        }
    }








}
