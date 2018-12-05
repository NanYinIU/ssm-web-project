package com.nanyin.controller;

import com.nanyin.common.annotation.Log;
import com.nanyin.common.util.EDSUtil;
import com.nanyin.entity.user.User;
import com.nanyin.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by NanYin on 2017-07-08 下午6:14.
 * 包名： com.nanyin.controller
 * 类描述：关于用户的信息的操作
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService ;

//  登录页 login
    @RequestMapping("/toLogin")
    @Log(operationType = "select操作",operationName = "用户登录")
    public String toLogin(String name, String password, HttpServletRequest request) {
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        String pass = null;
        if (password != null) {
            pass = EDSUtil.getEncryptString(password);
        }
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(name, pass);
            subject.login(token);
//            基于shiro的session控制
            HttpSession session = request.getSession();
            User user = userService.findUserByName(name);
            session.setAttribute("user", user);
            return  "/WEB-INF/jsp/admin/admin.jsp";
        } catch (Exception e) {
            request.setAttribute("error", "用户名或密码错误");
            return "/WEB-INF/jsp/login.jsp";
        }
    }

    //  登录页 login
    @RequestMapping("/login")
    @Log(operationType = "select操作",operationName = "用户登录")
    public String login() {
       return "/WEB-INF/jsp/login.jsp";
    }

    @RequestMapping("/logout")
    @Log(operationType = "select操作",operationName = "用户登出")
    public String logout(HttpServletRequest request){
//          清除session
        org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
        subject.getSession().stop();
        return "/WEB-INF/jsp/login.jsp" ;
    }
//    返回主页
    @RequestMapping("/index")
    public String returnIndex(){
        return "/WEB-INF/jsp/index.jsp";
    }

}
