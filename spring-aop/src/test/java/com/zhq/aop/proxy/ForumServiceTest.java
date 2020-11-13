package com.zhq.aop.proxy;

import org.junit.Test;

import java.lang.reflect.Proxy;

public class ForumServiceTest {

    @Test
    public void removeTopic() {
        ForumService forumService = new ForumServiceImpl();

        PerformanceHandler performanceHandler = new PerformanceHandler(forumService);
        ForumService proxy = (ForumService) Proxy.newProxyInstance(forumService.getClass().getClassLoader(),
                forumService.getClass().getInterfaces(), performanceHandler);
        proxy.removeTopic(10);
    }

    @Test
    public void testProxy() {
        CglibProxy cglibProxy = new CglibProxy();
        ForumServiceImpl forumService = (ForumServiceImpl) cglibProxy.getProxy(ForumServiceImpl.class);
        forumService.removeForum(10);
    }
}