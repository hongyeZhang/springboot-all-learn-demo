package com.zhq.aop.advice;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class GreetingAfterAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnObj, Method method, Object[] args,
							   Object obj) throws Throwable {
		System.out.println("Please enjoy yourself!");
	}
}
