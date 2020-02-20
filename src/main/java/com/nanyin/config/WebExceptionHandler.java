package com.nanyin.config;

import com.alibaba.fastjson.JSON;
import com.nanyin.config.util.Result;
import com.nanyin.config.enums.ResultCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * 统一异常处理
 **/

@RestControllerAdvice
public class WebExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 对hibernate validate进行统一的异常管理
     * @Author nanyin
     * @Date 20:06 2019-08-07
     **/
    @ResponseBody
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public String validationExceptionHandler(Exception exception) {
        BindingResult bindResult = null;
        if (exception instanceof BindException) {
            bindResult = ((BindException) exception).getBindingResult();
        } else if (exception instanceof MethodArgumentNotValidException) {
            bindResult = ((MethodArgumentNotValidException) exception).getBindingResult();
        }
        String msg;
        if (bindResult != null && bindResult.hasErrors()) {
            msg = bindResult.getAllErrors().get(0).getDefaultMessage();
            if (msg.contains("NumberFormatException")) {
                msg = "参数类型错误！";
            }
        }else {
            msg = "系统繁忙，请稍后重试...";
        }
        Result result = Result.resultInstance(ResultCodeEnum.FAIL,msg,bindResult);
        return JSON.toJSONString(result);
    }

}
