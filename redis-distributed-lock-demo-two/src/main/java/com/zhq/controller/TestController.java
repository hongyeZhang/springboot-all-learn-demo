package com.zhq.controller;

import com.zhq.service.RedisUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : ZHQ
 * @date : 2020/3/29
 */
@Slf4j
@RestController
public class TestController {

    @Autowired
    RedisUtils redisUtils;

    @RequestMapping(value = "/test/set")
    public String setValue(String key, String value) {
        boolean b = redisUtils.setValue(key, value);
        log.info("set value : " + b);
        return "success";
    }



}
