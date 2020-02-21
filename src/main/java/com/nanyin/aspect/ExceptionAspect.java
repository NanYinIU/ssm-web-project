package com.nanyin.aspect;

import com.nanyin.config.enums.ResultCodeEnum;
import com.nanyin.config.exceptions.CheckException;
import com.nanyin.config.exceptions.TokenExpiredException;
import com.nanyin.config.util.Result;
import org.aopalliance.intercept.Joinpoint;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Controller层的自动异常捕获
 */
@Aspect
@Component
public class ExceptionAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

    /**
     * com.nanyin.controlle 包下的所有方法，不限参数进行拦截
     */
    @Pointcut("execution(* com.nanyin.controller.*.*(..))")
    public void controllerAspect() {
    }


    @Around("controllerAspect()")
    public Object handleControllerMethods(ProceedingJoinPoint pjp) throws Throwable{
        // start stopwatch
        // 开始时间
        long startTime = System.currentTimeMillis();
        Result<?> result;
        try {
            result = (Result<?>) pjp.proceed();
            logger.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        } catch (Throwable e) {
            result = handlerException(pjp, e);
        }
        Object retVal = pjp.proceed();
        // stop stopwatch
        return result;
    }

    private Result<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        Result<?> result = new Result();

// 已知异常
        if (e instanceof CheckException) {
            result.setMessage(e.getLocalizedMessage());
            result.setCode(ResultCodeEnum.FAIL);
        }else if(e instanceof IncorrectCredentialsException){
            result.setMessage("密码错误！");
            result.setCode(ResultCodeEnum.FAIL);
        }else if(e instanceof TokenExpiredException){
            result.setMessage("令牌过期！");
            result.setCode(ResultCodeEnum.FAIL);
        }else {
            logger.error(pjp.getSignature() + " error ", e);
            result.setMessage(e.toString());
            result.setCode(ResultCodeEnum.FAIL);
        }
        return result;
    }


}
