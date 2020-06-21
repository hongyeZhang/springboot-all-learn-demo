package com.zhq.service;

import com.zhq.config.SpringContextHolder;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author ZHQ
 * @Date 2020/6/20 22:40
 */
@Service
public class HelloService {

    public String hello1() {
        return "hello1";
    }

    public String hello2() {
        return "hello2";
    }

    public String invokeSubHello() {
        return subHello();
    }


    public String subHello() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.submit(new Callable<String>() {
            @Override
            public String call() {
                String result = SpringContextHolder.getApplicationContext().getBean(HelloService.class).hello2();
                System.out.println(result);
                return result;
            }
        });

        return "subHello success";

    }

}
