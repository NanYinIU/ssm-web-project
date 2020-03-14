package com.nanyin.config.exceptions.exceptionHandler;

import com.google.common.base.Strings;
import com.nanyin.config.enums.ResultCodeEnum;
import com.nanyin.config.exceptions.tokenException.TokenExpiredException;
import com.nanyin.config.exceptions.tokenException.TokenWrongException;
import com.nanyin.config.util.Result;
import com.nanyin.services.LocaleService;
import org.apache.shiro.authz.UnauthenticatedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.Set;

/**
 * Controller层的自动异常捕获
 */
@Aspect
@Component
public class ControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @Autowired
    LocaleService localeService;
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
        return result;
    }

    private Result<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        Result<?> result = new Result();
        if(e instanceof ConstraintViolationException){
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
            Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
            if(iterator.hasNext()){
                ConstraintViolation<?> next = iterator.next();
                String code = next.getMessage();
                if(!Strings.isNullOrEmpty(code) && code.length() > 1){
                    code = code.substring(1,code.length()-1);
                }
                result.setMessage(code);
                result.setCode(ResultCodeEnum.FAIL);
            }
        }else if(e instanceof TokenExpiredException){
            result.setCode(ResultCodeEnum.TOKEN_EXPIRED);
            result.setMessage(e.getMessage());
        }else if(e instanceof TokenWrongException){
            result.setCode(ResultCodeEnum.WRONG_USERNAME_OR_PASSWORD);
        }else if(e instanceof UnauthenticatedException){
            result.setCode(ResultCodeEnum.NO_PERMISSION);
        }else{
            result.setMessage(e.getMessage());
            result.setCode(ResultCodeEnum.FAIL);
            e.printStackTrace();
        }

        logger.error("Occur Exception:"+e.getClass().getSimpleName()+",Message Show:"+e.getMessage());
        return result;
    }


}
