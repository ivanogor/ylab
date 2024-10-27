package homework3.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
public class AuditAspect {

    @Pointcut("execution(* homework3.service.impl.HabitServiceImpl.*(..))")
    public void habitServiceMethods() {}

    @Pointcut("execution(* homework3.service.impl.UserServiceImpl.*(..))")
    public void userServiceMethods() {}

    @AfterReturning(pointcut = "habitServiceMethods() || userServiceMethods()", returning = "result")
    public void logUserAction(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("User action: Class={}, Method={}, Result={}", className, methodName, result);
    }
}