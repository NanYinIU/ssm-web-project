package com.nanyin.config.exceptions.handler;

import com.nanyin.config.enums.ResultCodeEnum;
import com.nanyin.config.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AccessDeniedExceptionExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(AccessDeniedExceptionExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = {org.springframework.security.access.AccessDeniedException.class})
    public Result<String> authorizationExceptionHandler(Exception exception) {
        Result result = new Result<>();
        logger.error("Occur Exception:"+exception.getClass().getSimpleName()+",Message Show:"+exception.getMessage());
        result.setCode(ResultCodeEnum.NO_PERMISSION);
        result.setMessage(exception.getMessage());
        return result;
    }
}

