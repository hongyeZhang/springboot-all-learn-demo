package com.zhq.controller;

import com.zhq.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author ZHQ
 * @Date 2020/6/20 22:47
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping(value = "/hello")
    public String hello() throws InterruptedException {
        helloService.hello1();
        helloService.invokeSubHello();
        TimeUnit.SECONDS.sleep(1);
        return "success";
    }
}
