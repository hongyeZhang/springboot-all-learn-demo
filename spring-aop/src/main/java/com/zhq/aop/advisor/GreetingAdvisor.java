package com.zhq.aop.advisor;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import java.lang.reflect.Method;

public class GreetingAdvisor extends StaticMethodMatcherPointcutAdvisor {

    @Override
	public boolean matches(Method method, Class clazz) {
        return "greetTo".equals(method.getName());
    }

    @Override
	public ClassFilter getClassFilter() {
        return new ClassFilter() {
            @Override
			public boolean matches(Class clazz) {
                return Waiter.class.isAssignableFrom(clazz);
            }
        };

    }
}
