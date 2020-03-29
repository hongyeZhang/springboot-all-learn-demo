package com.zhq.thinkspringbootdemo.bean;

/**
 * @program: think-springboot-demo
 * @description:
 * @author: ZHQ
 * @create: 2019-07-26 21:04
 **/

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 后置处理器：初始化前后进行处理工作
 * 需要将后置处理器加入到容器中
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("postProcessBeforeInitialization..."+beanName+"..."+bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        System.out.println("postProcessAfterInitialization..."+beanName+"..."+bean);
        return bean;
    }

}
