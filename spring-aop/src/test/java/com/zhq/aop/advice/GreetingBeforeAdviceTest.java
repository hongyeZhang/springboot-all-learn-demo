package com.zhq.aop.advice;

import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;

import static org.junit.Assert.*;

public class GreetingBeforeAdviceTest {

    @Test
    public void test1() {
        Waiter naiveWaiter = new NaiveWaiter();
        GreetingBeforeAdvice greetingBeforeAdvice = new GreetingBeforeAdvice();
        GreetingAfterAdvice greetingAfterAdvice = new GreetingAfterAdvice();

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(naiveWaiter);
        proxyFactory.addAdvice(greetingBeforeAdvice);
        proxyFactory.addAdvice(greetingAfterAdvice);

        Waiter newWaiter = (Waiter) proxyFactory.getProxy();
        newWaiter.greetTo("zhq");

    }

    @Test
    public void test2() {
        Waiter naiveWaiter = new NaiveWaiter();
        GreetingInterceptor greetingInterceptor = new GreetingInterceptor();

        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTarget(naiveWaiter);
        proxyFactory.addAdvice(greetingInterceptor);

        Waiter newWaiter = (Waiter) proxyFactory.getProxy();
        newWaiter.greetTo("zhq");
    }


}