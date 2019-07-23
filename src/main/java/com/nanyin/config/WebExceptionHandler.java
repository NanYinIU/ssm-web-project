package com.nanyin.config;

import com.nanyin.config.exceptions.NoUserAccountException;
import com.nanyin.config.exceptions.UserIsBlockException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class WebExceptionHandler {
    @ExceptionHandler(value = UserIsBlockException.class)
    public String handlerNoAuthException(HttpServletRequest request){
        request.setAttribute("javax.servlet.error.status_code",402);
        Map<String,Object> map = new HashMap<>();
        map.put("code",401);
        map.put("message_zh","用户已经被锁定，请联系管理员！");
        map.put("message_en","The user has been locked, please contact the administrator!");
        request.setAttribute("errData",map);
        return "forward:/error";
    }

    @ExceptionHandler(value = NoUserAccountException.class)
    public String handlerNoUserException(HttpServletRequest request){
        request.setAttribute("javax.servlet.error.status_code",404);
        Map<String,Object> map = new HashMap<>();
        map.put("code",402);
        map.put("message_zh","系统中不存在这个用户，请联系管理员！");
        map.put("message_en","There is no such user in the system, please contact the administrator!");
        request.setAttribute("errData",map);
        return "forward:/error";
    }
}
