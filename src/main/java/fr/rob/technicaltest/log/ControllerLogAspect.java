package fr.rob.technicaltest.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class is used to log input and output of each call to a service method.
 * It also prints the time of executions in millisecond. This logger can be disabled in application.properties.
 */
@Aspect
@Component
@ConditionalOnProperty(prefix = "log", name = "controller")
public class ControllerLogAspect {
    private static final Logger logger = LoggerFactory.getLogger("CONTROLLER");

    @Around("execution(* fr.rob.technicaltest.controller.*.*(..))")
    public Object logBeforeAndAfter(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        String args = Arrays.stream(joinPoint.getArgs()).map(Object::toString).collect(Collectors.joining(","));

        logger.info("Calling method {}.{} with args {}", className, methodName, args);

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long totalExecutionTime = System.currentTimeMillis() - start;

        logger.info("Method {}.{} as returned {} after {} ms of execution", className, methodName, result, totalExecutionTime);
        return result;
    }
}
