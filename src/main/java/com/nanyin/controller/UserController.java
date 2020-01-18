package com.nanyin.controller;

import com.alibaba.fastjson.JSON;
import com.nanyin.config.operateLog.Log;
import com.nanyin.config.enums.OperateModuleEnum;
import com.nanyin.config.enums.OperationTypeEnum;
import com.nanyin.config.locale.LocaleService;
import com.nanyin.config.locale.MyCookieResolver;
import com.nanyin.config.redis.RedisService;
import com.nanyin.entity.*;
import com.nanyin.entity.DTO.UserDto;
import com.nanyin.entity.DTO.UserInfoDto;
import com.nanyin.entity.result.Result;
import com.nanyin.config.enums.ResultCodeEnum;
import com.nanyin.services.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController extends BasicController{

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

    @GetMapping("/user")
    public String userPage(Model model) {
        try {
            List<Sex> sex = userServices.findNotDeletedUserSex();
            List<Status> status = userServices.findNotDeletedUserStatus();
            List<Unit> unit = unitService.findNotDeletedUnit();
            List<Auth> auth = authService.findNotDeletedAuth();
            model.addAttribute("sex", sex);
            model.addAttribute("status", status);
            model.addAttribute("units", unit);
            model.addAttribute("auth", auth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回users页面
        return "users";
    }

    /**
     * 使用swapper相关注释注解来标识内容，用来显示到 /swapper-ui.html页面上。
     * @param search
     * @return
     */
    @GetMapping("/user/users")
    @Log(operationType = OperationTypeEnum.FIND, operateModul = OperateModuleEnum.USER, operationName = "search_users")
    @ResponseBody
    @ApiOperation(value = "find all users", notes = "用来查找所有用户")
    @ApiResponses({@ApiResponse(code = 200, message = "正常")
            , @ApiResponse(code = 401, message = "无权限")
            , @ApiResponse(code = 403, message = "禁止访问")
            , @ApiResponse(code = 404, message = "未知url")
            , @ApiResponse(code = 500, message = "内部错误")})
    public Result users(String search, Page page) {
        try {
            result = userServices.
                    findAllByIsDeleted(page.offset, page.limit, page.order, search);
        } catch (Exception e) {
            setMessage(e.toString());
        }
        return result;
    }

    @GetMapping("/user/lists")
    @Log(operationType = OperationTypeEnum.FIND, operateModul = OperateModuleEnum.USER, operationName = "search_users")
    @ResponseBody
    @ApiOperation(value = "find all users", notes = "用来查找所有用户")
    @ApiResponses({@ApiResponse(code = 200, message = "正常")
            , @ApiResponse(code = 401, message = "无权限")
            , @ApiResponse(code = 403, message = "禁止访问")
            , @ApiResponse(code = 404, message = "未知url")
            , @ApiResponse(code = 500, message = "内部错误")})
    public Result userLists(String search,Page page) {
        try {
            result = userServices.
                    findAllByIsDeleted(page.offset, page.limit, page.order, search);
        } catch (Exception e) {
            setMessage("异常");
        }
        return result;
    }


    @Log(operationType = OperationTypeEnum.FIND,operateModul = OperateModuleEnum.USER,operationName="search_user_info",params = {0})
    @GetMapping("/user/user/{id}")
    public String getUser(@PathVariable(name = "id") Integer id, Model model) {
        try {
            model.addAttribute("user",userServices.findUserById(id));
            model.addAttribute("sex",userServices.findNotDeletedUserSex());
        } catch (Exception e) {
            setMessage(e.toString());
            setCode(ResultCodeEnum.FAIL);
        }
        return "userInfo";
    }

    @PutMapping("/user/user/{id}")
    @ResponseBody
    public String putUser(@PathVariable(name = "id") Integer id, @RequestBody UserInfoDto userInfoDto, HttpServletRequest request) {
        try {
            User user = userServices.updateUser(id, userInfoDto);
            result = Result.resultInstance(user);
        } catch (Exception e) {
            result = Result.resultInstance(e);
            result.setMessage("自定义");
        }
        return JSON.toJSONString(result);
    }

    @PostMapping(value = "/user/user", consumes = "application/json")
    @ResponseBody
    public String addUser(@RequestBody UserDto user) {
        try {
            result = Result.resultInstance(userServices.addUser(user));
        } catch (Exception e) {
            result = Result.resultInstance(e);
        }
        return JSON.toJSONString(result);

    }

    @DeleteMapping("/user/user/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable(name = "id") Integer id) {
        try {
            result = userServices.deleteUser(id);
        } catch (Exception e) {
            result = Result.resultInstance(e);
        }
        return JSON.toJSONString(result);
    }

    @ApiOperation(value = "change passsword",notes = "修改用户密码")
    @PostMapping("/user/user/{id}/password")
    @ResponseBody
    public String changePassword(@PathVariable(name = "id") Integer id){
        try{
            userServices.changePassword(id);
        }catch (Exception e){
            e.printStackTrace();
            result = Result.resultInstance(e);
        }
        return JSON.toJSONString(result);
    }

    // 用户管理 结束


}

