package com.nhnacademy.edu.springframework.project.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimeAop {

    private static final Logger logger = LoggerFactory.getLogger(TimeAop.class);

    @Around("execution(* com.nhnacademy.edu.springframework.project..*.*(..))")
    public Object timeLogging(ProceedingJoinPoint point) throws Throwable {
        StopWatch stopWatch = new StopWatch("logging");

        try {
            stopWatch.start();
            Object proceed = point.proceed();

            return proceed;
        } finally {
            stopWatch.stop();
            logger.info("["+ point.getSignature().getDeclaringTypeName() +"].["+ point.getSignature().getName() + "] [" + stopWatch.getTotalTimeMillis() + "]ms");
        }
    }
}
