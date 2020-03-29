package com.zhq.lock.demo.controller;

import com.zhq.lock.demo.utils.RedisOperateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : ZHQ
 * @date : 2020/3/29
 */
@RestController
public class OperateController {

    @Autowired
    RedisOperateUtil redisOperateUtil;

    @RequestMapping(value = "/set1")
    public String setKeyValue(String key, String value) {
        redisOperateUtil.setKey(key, value);
        return "success";
    }


}
