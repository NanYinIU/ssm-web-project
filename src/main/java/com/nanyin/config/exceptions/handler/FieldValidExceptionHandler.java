package com.nanyin.config.exceptions.handler;

import com.google.common.base.Strings;
import com.nanyin.config.util.Result;
import com.nanyin.config.enums.ResultCodeEnum;
import com.nanyin.services.LocaleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

/**
 * 统一字段规则的异常处理
 **/

@RestControllerAdvice
public class FieldValidExceptionHandler {

    @Autowired
    LocaleService localeService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 对hibernate validate进行统一的异常管理
     * @Author nanyin
     * @Date 20:06 2019-08-07
     **/
    @ResponseBody
    @ExceptionHandler(value = {BindException.class, MethodArgumentNotValidException.class})
    public Result<String> validationExceptionHandler(Exception exception) {
        BindingResult bindResult = null;
        if (exception instanceof BindException) {
            bindResult = ((BindException) exception).getBindingResult();
        } else if (exception instanceof MethodArgumentNotValidException) {
            bindResult = ((MethodArgumentNotValidException) exception).getBindingResult();
        }
        String msg;
        if (bindResult != null && bindResult.hasErrors()) {
            String code = bindResult.getAllErrors().get(0).getDefaultMessage();
            // 把两边的 {} 去掉
            if(!Strings.isNullOrEmpty(code) && code.length() > 1){
                code = code.substring(1,code.length()-1);
            }
            msg =localeService.getMessage(code);

        }else {
            msg = "系统繁忙，请稍后重试...";
        }
        Result result = new Result<>();
        result.setMessage(msg);
        result.setCode(ResultCodeEnum.FAIL);
        return result;
    }

}
