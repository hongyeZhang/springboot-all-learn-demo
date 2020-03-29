package com.test.zha.redis.config;

/**
 * @program: redisTestNew
 * @description:
 * @author: ZHQ
 * @create: 2019-06-11 11:49
 **/
public class Pool {
    private int maxActive;
    private int maxWait;
    private int maxIdle;
    private int minIdle;

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }
}
