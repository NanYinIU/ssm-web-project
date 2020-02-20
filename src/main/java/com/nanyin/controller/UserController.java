package com.nanyin.controller;

import com.nanyin.config.log.Log;
import com.nanyin.config.enums.OperateModuleEnum;
import com.nanyin.config.enums.OperationTypeEnum;
import com.nanyin.config.locale.LocaleService;
import com.nanyin.config.locale.MyCookieResolver;
import com.nanyin.config.redis.RedisService;
import com.nanyin.config.util.HttpUtils;
import com.nanyin.config.util.Result;
import com.nanyin.entity.User;
import com.nanyin.services.*;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

@RestController
public class UserController{

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    ResourceService resourceService;
    @Autowired
    UserServices userServices;
    @Autowired
    RedisService redisService;

    @Autowired
    MyCookieResolver myCookieResolver;

    @Autowired
    LocaleService localeService;

    @Autowired
    UnitService unitService;
    @Autowired
    AuthService authService;

    @GetMapping("/user/lists")
    @Log(operationType = OperationTypeEnum.FIND, operateModul = OperateModuleEnum.USER, operationName = "search_users")
    @ResponseBody
    @ApiOperation(value = "find all users", notes = "用来查找所有用户")
    public Result userLists(String search,int offset, int limit, String order) throws Exception {
        return  userServices.
                    findAllByIsDeleted(offset, limit, order, search);
    }

    @GetMapping("/user/info")
    public Result userInfo() throws Exception {
        Cookie tokenKey = HttpUtils.getCookie("TokenKey");
        return new Result<User>(userServices.getCurrentUserInfo(tokenKey.getValue()));
    }

    // 用户管理 结束


}

