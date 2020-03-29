package com.zhq.spring.boot.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: spring-boot-demo
 * @description:
 * @author: ZHQ
 * @create: 2019-11-01 18:43
 **/
@RestController
public class LogTestController {
    private static final Logger logger = LoggerFactory.getLogger(LogTestController.class);

    @RequestMapping(value = "/log/test1")
    public String test1() {
        System.out.println("log method");
        return "success";
    }

    @RequestMapping(value = "/test2")
    public String test2() {
        System.out.println("test2 method");
        return "success";
    }



}
