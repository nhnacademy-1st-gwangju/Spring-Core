package com.nhnacademy.edu.springframework.project.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimeAop {

    @Around("execution(* com.nhnacademy.edu.springframework.project.service.*.*(..))")
    public Object timeLogging(ProceedingJoinPoint point) throws Throwable {
        StopWatch stopWatch = new StopWatch("logging");

        try {
            stopWatch.start();
            Object proceed = point.proceed();

            return proceed;
        } finally {
            stopWatch.stop();
            System.out.println("["+ point.getSignature().getDeclaringTypeName() +"].["+ point.getSignature().getName() + "] [" + stopWatch.getTotalTimeMillis() + "]ms");
        }
    }
}
