package com.nanyin.controller;

import com.nanyin.config.exceptions.UserIsBlockException;
import com.nanyin.config.util.SessionUtil;
import com.nanyin.enumEntity.MessageEnum;
import com.nanyin.services.ResourceServices;
import com.nanyin.services.UserServices;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    ResourceServices resourceServices;
    @Autowired
    UserServices userServices;

//    登陆注册部分开始 -------------------------------------------------------

    @GetMapping("/signin")
    public String signin(String language, Model model, HttpServletRequest request){
        if(language == null){
            language = (String) SessionUtil.getSession().getAttribute("language");
            if(language == null){
                String lan = request.getHeader("Accept-Language").split(",")[0];
                language = lan;
            }
        }
        model.addAttribute("language",language);
        return "signin";
    }

    @GetMapping("/users/user/{id}")
    public String user(@PathVariable int id){
        return "";
    }

    @GetMapping("/index")
    public String index(String language){
        if(language == null){
            language = (String) SessionUtil.getSession().getAttribute("language");
        }
        SessionUtil.setAttribute("language",language);
        return "index";
    }

    @PostMapping("/login")
    public String login(String username,String password,String language,Model model,HttpServletRequest request){
        // shiro验证
        Subject subject = SecurityUtils.getSubject();
        model.addAttribute("language",language);
        if(username == null ){
            return "signin";
        }
        SavedRequest savedRequest= WebUtils.getSavedRequest(request);

        if(!subject.isAuthenticated()){
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
            try{
                subject.login(usernamePasswordToken);
                SessionUtil.setAttribute("username", username).setAttribute("language",language);
                SessionUtil.setAttribute("sidebar",resourceServices.getSidebarInfoWapper()) ;
                if(null!=savedRequest){
                    return "redirect:" + savedRequest.getRequestUrl();
                }else{
                    return "redirect:/index?language="+language;
                }
            }catch (UserIsBlockException u){
                model.addAttribute("msg", MessageEnum.USER_HAS_BEEN_BLOCKED.toString());
                usernamePasswordToken.clear();
                return "signin";
            }catch (Exception e){
                model.addAttribute("msg", MessageEnum.USERNAME_OR_PASSWORD_WRONG.toString());
                return "signin";
            }
        }else{
            if(null!=savedRequest){
                return "redirect:" + savedRequest.getRequestUrl().substring(8);
            }else{
                return "redirect:/index?language="+language;
            }
        }
    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        String language = (String) SessionUtil.getAttribute("language");
        subject.logout();
        return "redirect:/signin?language="+language;
    }

    //    登陆注册部分结束 -------------------------------------------------------

}
