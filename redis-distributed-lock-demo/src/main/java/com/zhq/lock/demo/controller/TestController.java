package com.zhq.lock.demo.controller;


import com.zhq.lock.demo.annotation.RedisLock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author ZHQ
 */
@RestController
public class TestController {

    private int num = 0;

    @RedisLock(key = "zhq-distribute-lock", expire = 300, waitTime = 3)
    @GetMapping("/add")
    public String addNum() {
        num++;

        return "index";
    }

    @GetMapping("/get")
    public int getNum() {
        return num;
    }


}
