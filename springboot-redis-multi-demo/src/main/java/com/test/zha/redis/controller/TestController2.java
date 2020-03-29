package com.test.zha.redis.controller;

import com.test.zha.redis.util.RedisUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: redisTestNew
 * @description:
 * @author: ZHQ
 * @create: 2019-09-24 14:32
 **/
@RestController
public class TestController2 {

    @RequestMapping(value = "/test2/set1")
    public String setTest1() {
        RedisUtils.setValue("zhq-cms-test", "1");
        return "success";
    }

    @RequestMapping(value = "/test2/get1")
    public String getTest1() {
        return (String) RedisUtils.getValue("zhq-cms-test");
    }



}
