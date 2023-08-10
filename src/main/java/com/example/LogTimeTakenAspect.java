package com.sc.csl.retail.core.aspects;

import com.sc.csl.retail.core.log.LogTimeTaken;
import com.sc.csl.retail.core.log.LogTimeTaken.Level;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

import static com.sc.csl.retail.core.util.CSLLogConstants.*;
import static net.logstash.logback.argument.StructuredArguments.kv;

@Aspect
@Slf4j
public class LogTimeTakenAspect {

    @Around("execution(* *(..)) && @annotation(com.sc.csl.retail.core.log.LogTimeTaken)")
    public Object logTimeTaken(ProceedingJoinPoint joinPoint) throws Throwable {
    	long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - start;
        
        Method method = MethodSignature.class.cast(joinPoint.getSignature()).getMethod();
        LogTimeTaken annotation = method.getAnnotation(LogTimeTaken.class);
    	String methodSignature = method.getDeclaringClass().getName() + ":" + method.getName();

        logMessage(annotation.level(), methodSignature, timeTaken);
        return result;
    }
    
    public static void logMessage(Level logLevel, String methodSignature, long timeTaken, String key) {
    	String msg = "Time taken by {} : {} ms [{}]";
        if (logLevel == Level.INFO) {
            log.info(msg, kv(METHOD_SIGNATURE, methodSignature),
                          kv(TIME_TAKEN_IN_MILLIS, timeTaken),
                          kv(PERFORMANCE_METRIC, key));
        }
        else {
            log.debug(msg, kv(METHOD_SIGNATURE, methodSignature),
                           kv(TIME_TAKEN_IN_MILLIS, timeTaken),
                           kv(PERFORMANCE_METRIC, key));
        }
    }

    public static void logMessage(Level logLevel, String methodSignature, long timeTaken) {
        logMessage(logLevel, methodSignature, timeTaken, LOG_TIME_TAKEN_KEY);
    }
}
