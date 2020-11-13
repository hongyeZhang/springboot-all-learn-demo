package com.zhq.aop.advisor;

import org.junit.Test;
import org.springframework.aop.framework.ProxyFactory;

public class GreetingAdvisorTest {

    @Test
    public void test1() {
        ProxyFactory proxyFactory = new ProxyFactory();
        Waiter waiter = new Waiter();
        GreetingBeforeAdvice greetingBeforeAdvice = new GreetingBeforeAdvice();
        GreetingAdvisor greetingAdvisor = new GreetingAdvisor();
        greetingAdvisor.setAdvice(greetingBeforeAdvice);
        proxyFactory.setTarget(waiter);
        proxyFactory.addAdvisor(greetingAdvisor);
        proxyFactory.setProxyTargetClass(true);

        Waiter proxy = (Waiter) proxyFactory.getProxy();
        proxy.greetTo("zhq");
//        proxy.serveTo("zhq");
    }

}