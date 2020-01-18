package com.nanyin.controller;

import com.alibaba.fastjson.JSON;
import com.nanyin.config.exceptions.CheckException;
import com.nanyin.config.locale.LocaleService;
import com.nanyin.config.locale.MyCookieResolver;
import com.nanyin.config.util.HttpsUtil;
import com.nanyin.config.util.MDCUtil;
import com.nanyin.entity.result.Result;
import com.nanyin.entity.Resource;
import com.nanyin.services.ResourceService;
import com.nanyin.services.UserServices;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

/**
 * Created by NanYin on 2019/9/10.
 * 用于登录、登出等操作
 * 并非前后端分离项目
 */
@Controller
public class LoginController {

    @Autowired
    MyCookieResolver myCookieResolver;
    @Autowired
    UserServices userServices;
    @Autowired
    ResourceService resourceService;
    @Autowired
    LocaleService localeService;

//    登陆注册部分开始 -------------------------------------------------------
    @GetMapping("/signin")
    @ApiOperation(value = "跳转登录页",notes = "跳转登录页")
    public String signin(HttpServletRequest request) {
        // 因为默认的登陆页面，所以从这里开始，看session里有没有 lang信息，如果session中有，则把session中的取出来，放到MDC中
        // 如果设置了language,则将language设置都session中，
        Locale locale = RequestContextUtils.getLocale(request);
        MDCUtil.setLocale(locale);
        HttpsUtil.getSession().setAttribute("language",locale);
        return "signin";
    }

    @GetMapping("/index")
    @ApiOperation(value = "跳转首页",notes = "跳转首页")
    public String index() {
        return "index";
    }

    @PostMapping("/login")
    @ApiOperation(value = "登录",notes = "登录")
    public String login(String username, String password, Boolean rememberMe,
                        Model model, HttpServletRequest request, HttpServletResponse response, String locale) {
        MDCUtil.setUser(username);
        try {
            List<Resource> sidebarInfoWapper = resourceService.getSidebarInfoWapper(username);
            return userServices.doLogin(username, password, rememberMe, locale, request, response, sidebarInfoWapper);
        } catch (CheckException c){
            model.addAttribute("message", c.getMessage());
            MDCUtil.clearAllUserInfo();
            return "signin";
        }catch (AuthenticationException in) {
            model.addAttribute("message", localeService.getMessage("no_account"));
            MDCUtil.clearAllUserInfo();
            return "signin";
        } catch (Exception ex) {
            ex.printStackTrace();
            model.addAttribute("message", localeService.getMessage("system_error"));
            MDCUtil.clearAllUserInfo();
            return "signin";
        }

    }

    @PostMapping("/lang")
    @ApiOperation(value = "切换语言",notes = "切换语言")
    public @ResponseBody
    String transferLang(HttpServletRequest request, HttpServletResponse response, String lang){
        myCookieResolver.setLocale(request,response, MDCUtil.getLocale(lang));
        HttpsUtil.getSession().setAttribute("language",MDCUtil.getLocale(lang));
        MDCUtil.setLocale(lang);
        Result result = Result.resultInstance(lang);
        return JSON.toJSONString(result);
    }

    @GetMapping("/logout")
    @ApiOperation(value = "登出",notes = "登出")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/signin";
    }
    //    登陆注册部分结束 -------------------------------------------------------

}
