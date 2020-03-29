package com.zhq.spring.boot.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author : ZHQ
 * @date :
 */

@Aspect
@Component
public class LogAspect {

    private static Logger logger;
    private static String LOG_CLASS_NAME;

    @Pointcut("execution(public * com.zhq.spring.boot.demo.controller.AspectTestController.activityGroup*(..))")
    public void log() {
    }

    @Before(value = "log()")
    public void beforeAdvice(JoinPoint joinPoint) {
//        logger.info("[Aspect] before advise");
    }

    @Around(value = "log()")
    public void aroundAdvice(ProceedingJoinPoint pjp) throws  Throwable{
        LOG_CLASS_NAME = getClassWholeName(pjp);
        logger = LoggerFactory.getLogger(LOG_CLASS_NAME);
        String methodName = getMethodName(pjp);
        logger.info("[Aspect] around before " + methodName);
        pjp.proceed();
        logger.info("[Aspect] around after " + methodName);
    }

    @AfterReturning(value = "log()")
    public void afterReturningAdvice(JoinPoint joinPoint) {
        logger.info("[Aspect] afterReturning advise");
    }

    @AfterThrowing(value = "log()", throwing = "exception")
    public void afterThrowingAdvice(JoinPoint joinPoint, Throwable exception) {
        logger.info("[Aspect] afterThrowing advise");
        if (exception instanceof NullPointerException) {
            System.out.println("null pointer exception");
        }
    }

    @After(value = "log()")
    public void afterAdvice(JoinPoint joinPoint) {
        logger.info("[Aspect] after advise");
    }


    /**
     * 获得调用的方法名称
     * @param joinPoint
     * @return
     */
    private String getInvokedMethodName(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String[] nameArray = signature.getDeclaringTypeName().split("\\.");
        String classShortName = nameArray[nameArray.length - 1];

        return new StringBuilder(classShortName).append("-").append(methodName).toString();
    }

    private String getClassWholeName(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        return signature.getDeclaringTypeName();
    }

    private String getMethodName(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        return signature.getName();
    }





}
