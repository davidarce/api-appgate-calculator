package com.appgate.api.calculator.interceptors;

import com.appgate.api.calculator.exception.AppgateException;
import com.appgate.api.calculator.models.TransactionActions;
import com.appgate.api.calculator.services.TransactionTraceService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Aspect
@Service
public class TransactionLoggerInterceptor {

    private final TransactionTraceService transactionTraceService;

    public TransactionLoggerInterceptor(TransactionTraceService transactionTraceService) {
        this.transactionTraceService = transactionTraceService;
    }

    @AfterReturning(
            pointcut = "@annotation(com.appgate.api.calculator.interceptors.TransactionLogger)",
            returning = "retVal")
    public void ok(JoinPoint jp, Object retVal) {
        TransactionActions action = resolveAction(jp);
        String sessionId = resolveSessionId(jp, retVal);
        transactionTraceService.ok(sessionId, action);
    }

    @AfterThrowing(
            pointcut = "@annotation(com.appgate.api.calculator.interceptors.TransactionLogger)",
            throwing = "ex"
    )
    public void processException(JoinPoint jp, Throwable ex) throws AppgateException {
        TransactionActions action = resolveAction(jp);
        String sessionId = resolveSessionId(jp, null);
        transactionTraceService.error(sessionId, action, ex.getMessage());

    }

    private TransactionActions resolveAction(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        TransactionLogger transactionLogger = method.getAnnotation(TransactionLogger.class);
        return transactionLogger.action();
    }

    private String resolveSessionId(JoinPoint jp, Object retVal) {
        TransactionActions action = resolveAction(jp);
        String sessionId;

        switch (action) {
            case CREATE_SESSION:
                sessionId = (String) retVal;
                break;
            case ADD_OPERAND:
            case CALCULATE_OPERATION:
                sessionId = (String) jp.getArgs()[0];
                break;
            default:
                sessionId = "unknown session";
        }

        return sessionId;
    }

}
