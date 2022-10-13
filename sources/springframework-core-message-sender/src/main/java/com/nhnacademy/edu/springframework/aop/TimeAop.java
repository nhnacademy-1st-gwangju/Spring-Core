package com.nhnacademy.edu.springframework.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimeAop {

    @Around("execution(* com.nhnacademy.edu.springframework.messagesender..sendMessage(..))")
    public Object timeLogging(ProceedingJoinPoint point) throws Throwable {
        StopWatch stopWatch = new StopWatch("Logging");

        try {
            System.out.println("------time Logging start------");
            stopWatch.start();
            Object proceed = point.proceed();

            return proceed;
        } finally {
            stopWatch.stop();
            System.out.println(stopWatch.prettyPrint());
        }
    }

    @Before("@annotation(AopTest)")
    public void test() {
//        System.out.println(aopTest.value());
    }
}
