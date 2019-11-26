package com.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * aop 方法計時
 * @author AlbertXe
 * @date 2019-11-25 17:38
 */
@Aspect
@Component
public class LogMethodTimes {
    private  static  final Logger logger = LoggerFactory.getLogger(LogMethodTimes.class);
    @Pointcut("@annotation(com.aop.CountTimes)")
    public void annotationPointCut() { }

    @Around("annotationPointCut()")
    public Object count(ProceedingJoinPoint point) {
        long s = System.currentTimeMillis();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        String name = method.getName();

        Object proceed = null;
        try {
            proceed = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long e = System.currentTimeMillis();
        logger.info("{}方法耗時：{}ms",name,e-s);
        return proceed;
    }
}
