package com.zhq;

import com.zhq.config.AppConfig;
import com.zhq.dao.OrderDao;
import com.zhq.dao.UserDao;
import com.zhq.service.OrderService;
import com.zhq.service.UserService;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author : ZHQ
 * @date : 2020/4/5
 */
public class SimpleSourceCodeDemoApplication {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = applicationContext.getBean(UserService.class);
        System.out.println(userService);

        OrderService orderService = applicationContext.getBean(OrderService.class);
        System.out.println(orderService);

        UserDao userDao = applicationContext.getBean(UserDao.class);
        System.out.println(userDao);

        OrderDao orderDao = applicationContext.getBean(OrderDao.class);
        System.out.println(orderDao);

        applicationContext.close();
//        applicationContext.registerShutdownHook();

    }
}
