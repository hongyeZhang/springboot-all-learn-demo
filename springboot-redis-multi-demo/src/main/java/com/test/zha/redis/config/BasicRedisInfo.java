package com.test.zha.redis.config;

/**
 * @program: redisTestNew
 * @description:
 * @author: ZHQ
 * @create: 2019-06-11 11:52
 **/
public class BasicRedisInfo {
    private int database;
    private String host;
    private int port;
    private String password;
    private int timeout;
    private Pool pool;

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }
}
