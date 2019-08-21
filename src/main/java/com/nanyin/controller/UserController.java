package com.nanyin.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.nanyin.config.exceptions.NoUserAccountException;
import com.nanyin.config.exceptions.UserIsBlockException;
import com.nanyin.config.redis.RedisService;
import com.nanyin.config.util.*;
import com.nanyin.entity.*;
import com.nanyin.entity.dto.UserDto;
import com.nanyin.entity.dto.UserInfoDto;
import com.nanyin.enumEntity.MessageEnum;
import com.nanyin.services.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
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
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
    @Autowired
    SocialMediaServices socialMediaServices;

//    登陆注册部分开始 -------------------------------------------------------

    @GetMapping("/signin")
    public String signin(String language, Model model, HttpServletRequest request,HttpServletResponse response) {
        // 因为默认的登陆页面，所以从这里开始，看session里有没有 lang信息，如果session中有，则把session中的取出来，放到MDC中
        // 如果设置了language,则将language设置都session中，
        Locale locale = MDCUtil.setLocale(request, response, language);
        model.addAttribute("language",locale);
        return "signin";
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    public String login(String username, String password, Boolean rememberMe,
                        Model model, HttpServletRequest request, HttpServletResponse response, String locale) {
        MDCUtil.setUser(username);
        try {
            List<Resource> sidebarInfoWapper = resourceServices.getSidebarInfoWapper(username);
            return userServices.doLogin(username, password, rememberMe, locale, request, response, sidebarInfoWapper);
        } catch (IncorrectCredentialsException in) {
            model.addAttribute("msg", MessageEnum.INCORRECT_LOGIN_INFORMATION.toString());
            MDCUtil.clearAllUserInfo();
            return "signin";
        } catch (Exception ex) {
            model.addAttribute("msg", MessageEnum.SYSTEM_ERROR.toString());
            logger.info("error message :{}", ex.getMessage());
            MDCUtil.clearAllUserInfo();
            return "signin";
        }

    }

    @PostMapping("/lang")
    public @ResponseBody String transferLang(HttpServletRequest request,HttpServletResponse response,String lang){
        Locale locale = MDCUtil.setLocale(request, response, lang);
        Result result = Result.resultInstance(locale);
        return JSON.toJSONString(result);
    }

    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/signin";
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
    public String users(Integer offset, Integer limit, String order, String search) {
        Result result = null;
        try {
            List<User> allUsersButNotDeleted = userServices.
                    findAllByIsDeleted(offset, limit, order, search);
            Map resultMap = ResultMap.generateInstance(userServices.countAllByIsDeleted(search), allUsersButNotDeleted);
            result = Result.resultInstance(resultMap);
        } catch (Exception e) {
            result = Result.resultInstance(e);
        }
        return JSON.toJSONString(result);
    }

    @GetMapping("/user/user/{id}")
    public String getUser(@PathVariable(name = "id") Integer id, Model model) {
        Result result = null;
        try {
            HashMap<String, Object> data = Maps.newHashMap();
            data.put("sex", userServices.findNotDeletedUserSex());
            data.put("user", userServices.findUserById(id));
            data.put("socialType", socialMediaServices.getSupportSocialType());
            result = Result.resultInstance(data);
        } catch (Exception e) {
            result = Result.resultInstance(e);
        }
        model.addAttribute(result);
        return "userInfo";
    }


    @PutMapping("/user/user/{id}")
    @ResponseBody
    public String putUser(@PathVariable(name = "id") Integer id, @RequestBody UserInfoDto userInfoDto, HttpServletRequest request) {
        Result result = null;
        try {
            String[] socialMedia = {};
            List<SocialType> supportSocialType = socialMediaServices.getSupportSocialType();
            for (int i = 0; i < supportSocialType.size(); i++) {
                String name = supportSocialType.get(i).getName();
                socialMedia[i] = request.getParameter("modify-" + name);
            }
            userInfoDto.setSocialMedia(socialMedia);
            userServices.updateUser(id, userInfoDto);
            result = Result.resultInstance();
        } catch (Exception e) {
            result = Result.resultInstance(e);
            result.setMsg("自定义");
        }
        return JSON.toJSONString(result);
    }

    @PostMapping(value = "/user/user", consumes = "application/json")
    @ResponseBody
    public String addUser(@RequestBody UserDto user) {
        Result result = null;
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

