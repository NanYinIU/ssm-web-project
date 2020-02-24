package com.nanyin.controller;

import com.nanyin.config.enums.ResultCodeEnum;
import com.nanyin.config.exceptions.TokenWrongException;
import com.nanyin.services.LocaleService;
import com.nanyin.config.locale.MyCookieResolver;
import com.nanyin.services.RedisService;
import com.nanyin.entity.DTO.NameAndPw;
import com.nanyin.config.util.Result;
import com.nanyin.services.UserServices;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by NanYin on 2019/9/10.
 * 用于登录、登出等操作
 * 并非前后端分离项目
 */
@Controller
public class LoginController{


    @Autowired
    UserServices userServices;

    @Autowired
    LocaleService localeService;

    @Autowired
    RedisService redisService;

//    @CrossOrigin(origins = "*",allowCredentials="true",allowedHeaders = "",methods = {})
    @PostMapping(value = "/user/login")
    public @ResponseBody
    Result login(@RequestBody NameAndPw nameAndPw) {
//        Boolean rememberMe = false;
        String data = userServices.login(nameAndPw.getUsername(),nameAndPw.getPassword(),false);
        if(data.equals(ResultCodeEnum.WRONG_USERNAME_OR_PASSWORD.toString())){
            throw new TokenWrongException();
        }
        return new Result<>(data);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "登出",notes = "登出")
    public Result logout(@RequestParam String token) throws Exception {
        String data = userServices.logout(token);
        return new Result<>(data);
    }

}
