package com.zhq.thinkspringbootdemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: think-springboot-demo
 * @description:
 * @author: ZHQ
 * @create: 2019-07-26 19:53
 **/
@RestController
public class HelloController {


    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello, world";
    }

    public static void test() {
        StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            System.out.println(stackTraceElement.toString());
        }

    }


    public static void main(String[] args) {
        test();
    }
}
