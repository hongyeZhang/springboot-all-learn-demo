package com.zhq.lock.demo.bean;


import com.zhq.lock.demo.threadpool.ThreadPoolUtil;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

/**
 * @author : ZHQ
 * @date : 2020/3/29
 */
@Component
public class BeanConfig {

    @Bean
    public ExecutorService lockExecutorService() {
        return ThreadPoolUtil.newThreadPool(30, 30);
    }
}
