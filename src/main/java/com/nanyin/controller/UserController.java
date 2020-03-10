package com.nanyin.controller;

import com.nanyin.config.log.Log;
import com.nanyin.config.enums.OperateModuleEnum;
import com.nanyin.config.enums.OperationTypeEnum;
import com.nanyin.services.RedisService;
import com.nanyin.config.util.HttpUtils;
import com.nanyin.config.util.Result;
import com.nanyin.entity.User;
import com.nanyin.services.*;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    UserServices userServices;
    @Autowired
    RedisService redisService;

    @GetMapping("/users")
    @RequiresUser
    @Log(operationType = OperationTypeEnum.FIND, operateModul = OperateModuleEnum.USER, operationName = "search_users")
    @ResponseBody
    @ApiOperation(value = "查找用户列表")
    public Result userLists(String search, Integer offset, Integer limit, String sort, Integer status, Integer sex, Integer role) throws Exception {
        //todo 认证授权取不到值
        Subject subject = SecurityUtils.getSubject();
        System.out.println(subject.getPrincipal());
        System.out.println(subject.hasRole("admin"));
        return new Result<>(userServices.findUsers(offset, limit, sort, search, status, sex, role));
    }

    @GetMapping("/user/info")
    @ApiOperation(value = "获取当前用户详细信息")
    public Result userInfo() throws Exception {
        Cookie tokenKey = HttpUtils.getCookie("TokenKey");
        return new Result<>(userServices.getCurrentUserInfo(tokenKey.getValue()));
    }

    @GetMapping("/user/sex")
    @ApiOperation(value = "获取用户【性别】属性详细信息")
    public Result userStandardSex() throws Exception {
        return new Result<>(userServices.getStandardSex());
    }

    @GetMapping("/user/status")
    @ApiOperation(value = "获取用户【状态】属性详细信息")
    public Result userStandardStatus() throws Exception {
        return new Result<>(userServices.getStandardStatus());
    }

    /**
     * 使用@Valid注解验证字段
     *
     * @param user
     * @return
     * @throws Exception
     */
    @PostMapping("/user")
    @RequiresRoles(value={"admin"},logical= Logical.OR)
    @ApiOperation(value = "添加用户信息")
    public Result saveUser(@Valid @RequestBody User user) throws Exception {
        return new Result<>(userServices.saveUser(user));
    }

    @PutMapping("/user/{id}")
    @ApiOperation(value = "修改用户信息")
    public Result updateUser(@Valid @RequestBody User user, @PathVariable("id") Integer userId) throws Exception {
        return new Result<>(userServices.updateUser(user));
    }

    @DeleteMapping("/user/{id}")
    @ApiOperation(value = "删除用户信息")
    public Result deleteUser(@PathVariable("id") Integer userId) throws Exception {
        userServices.deleteUser(userId);
        return new Result<>();
    }


    // 用户管理 结束


}

