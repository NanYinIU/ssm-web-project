package com.nanyin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nanyin.config.exceptions.NoUserAccountException;
import com.nanyin.config.exceptions.UserIsBlockException;
import com.nanyin.config.redis.RedisService;
import com.nanyin.config.util.Result;
import com.nanyin.config.util.ResultMap;
import com.nanyin.config.util.SessionUtil;
import com.nanyin.entity.*;
import com.nanyin.enumEntity.HttpMsgEnum;
import com.nanyin.enumEntity.MessageEnum;
import com.nanyin.services.AuthService;
import com.nanyin.services.ResourceServices;
import com.nanyin.services.UnitService;
import com.nanyin.services.UserServices;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    ResourceServices resourceServices;
    @Autowired
    UserServices userServices;
    @Autowired
    RedisService redisService;

//    登陆注册部分开始 -------------------------------------------------------

    @GetMapping("/signin")
    public String signin(String language, Model model, HttpServletRequest request) {
        try {
            if (language == null) {
                language = (String) SessionUtil.getSession().getAttribute("language");
                if (language == null) {
                    String lan = request.getHeader("Accept-Language").split(",")[0];
                    language = lan;
                }
            }
            model.addAttribute("language", language);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "signin";
    }

    @GetMapping("/index")
    public String index(String language) {
        if (language == null) {
            language = (String) SessionUtil.getSession().getAttribute("language");
        }
        SessionUtil.setAttribute("language", language);
        return "index";
    }

    @PostMapping("/login")
    public String login(String username, String password, String language, Boolean rememberMe, Model model, HttpServletRequest request) {
        // shiro验证
        Subject subject = SecurityUtils.getSubject();
        model.addAttribute("language", language);
        if (username == null) {
            return "signin";
        }
        if (rememberMe == null) {
            rememberMe = false;
        }
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password, rememberMe);
            try {
                subject.login(usernamePasswordToken);
                SessionUtil.setAttribute("username", username).setAttribute("language", language);
                List<Resource> sidebarInfoWapper = resourceServices.getSidebarInfoWapper();
                SessionUtil.setAttribute("sidebar", sidebarInfoWapper);
                if (null != savedRequest) {
                    return "redirect:" + savedRequest.getRequestUrl();
                } else {
                    return "redirect:/index?language=" + language;
                }
            } catch (UserIsBlockException u) {
                model.addAttribute("msg", MessageEnum.USER_HAS_BEEN_BLOCKED.toString());
                usernamePasswordToken.clear();
                return "signin";
            } catch (NoUserAccountException e) {
                model.addAttribute("msg", MessageEnum.USERNAME_OR_PASSWORD_WRONG.toString());
                return "signin";
            } catch (Exception ex) {
                model.addAttribute("msg", MessageEnum.SYSTEM_ERROR.toString());
                return "signin";
            }
        } else {
            if (null != savedRequest) {
                return "redirect:" + savedRequest.getRequestUrl().substring(8);
            } else {
                return "redirect:/index?language=" + language;
            }
        }
    }

    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        String language = (String) SessionUtil.getAttribute("language");
        subject.logout();
        return "redirect:/signin?language=" + language;
    }

    //    登陆注册部分结束 -------------------------------------------------------

    // 用户管理 开始

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

    @GetMapping("/user/users")
    @ResponseBody
    public String users(Integer offset, Integer limit, String order,String search) {
        Result result = null;
        try{
            List<User> allUsersButNotDeleted = userServices.
                    findAllByIsDeleted(offset, limit, order,search);
            Map resultMap = ResultMap.generateInstance(userServices.countAllByIsDeleted(search), allUsersButNotDeleted);
            result = Result.resultInstance(resultMap);
        }catch (Exception e){
            result = Result.resultInstance(e);
        }
        return JSON.toJSONString(result);
    }

    @GetMapping("/user/user/{id}")
    @ResponseBody
    public String getUser(@PathVariable(name = "id") Integer id) {
        Result result = null;
        try{
            result = Result.resultInstance(userServices.findUserById(id));
        }catch (Exception e){
            result = Result.resultInstance(e);
        }
        return JSON.toJSONString(result);
    }


    @PutMapping("/user/user/{id}")
    @ResponseBody
    public String putUser(@PathVariable(name = "id") Integer id, String name, String email,
                              @RequestParam(required = false) int sex, @RequestParam(required = false) int status,
                              @RequestParam(value = "auth[]", required = false) int[] auth) {
        Result result = null;
        try{
            result = Result.resultInstance(userServices.updateUser(id, name, email, sex, status, auth));
        }catch (Exception e){
            result = Result.resultInstance(e);
            result.setMsg("自定义");
        }
        return JSON.toJSONString(result);
    }

    @PostMapping("/user/user")
    @ResponseBody
    public String addUser(String name, String email,
                          @RequestParam(required = false) int sex,
                          @RequestParam(required = false) int status,
                          @RequestParam(value = "auth[]", required = false) int[] auth) {
        Result result = null;
        try{
            result = Result.resultInstance(userServices.addUser(name, email, sex, status, auth));
        }catch (Exception e){
            result = Result.resultInstance(e);
        }
        return JSON.toJSONString(result);

    }

    @DeleteMapping("/user/user/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable(name = "id") Integer id) {
        Result result = Result.resultInstance();
        try {
            userServices.deleteUser(id);
        } catch (Exception e) {
            result = Result.resultInstance(e);
        }
        return JSON.toJSONString(result);
    }

    // 用户管理 结束


}

