package com.nanyin.controller;

import com.alibaba.fastjson.JSONObject;
import com.nanyin.config.exceptions.NoUserAccountException;
import com.nanyin.config.exceptions.UserIsBlockException;
import com.nanyin.config.util.SessionUtil;
import com.nanyin.entity.*;
import com.nanyin.entity.dto.UserDto;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public String login(String username,String password,String language,Boolean rememberMe,Model model,HttpServletRequest request){
        // shiro验证
        Subject subject = SecurityUtils.getSubject();
        model.addAttribute("language",language);
        if(username == null ){
            return "signin";
        }
        if(rememberMe == null){
            rememberMe = false;
        }
        SavedRequest savedRequest= WebUtils.getSavedRequest(request);
        if(!subject.isAuthenticated()){
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password,rememberMe);
            try{
                subject.login(usernamePasswordToken);
                SessionUtil.setAttribute("username", username).setAttribute("language",language);
                List<Resource> sidebarInfoWapper = resourceServices.getSidebarInfoWapper();
                SessionUtil.setAttribute("sidebar",sidebarInfoWapper) ;
                if(null!=savedRequest){
                    return "redirect:" + savedRequest.getRequestUrl();
                }else{
                    return "redirect:/index?language="+language;
                }
            }catch (UserIsBlockException u){
                model.addAttribute("msg", MessageEnum.USER_HAS_BEEN_BLOCKED.toString());
                usernamePasswordToken.clear();
                return "signin";
            }catch (NoUserAccountException e){
                model.addAttribute("msg", MessageEnum.USERNAME_OR_PASSWORD_WRONG.toString());
                return "signin";
            }catch (Exception ex){
                model.addAttribute("msg", MessageEnum.SYSTEM_ERROR.toString());
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



    // 用户管理 开始

    @Autowired
    UnitService unitService;
    @Autowired
    AuthService authService;

    @GetMapping("/user")
    public String userPage(Model model){
        List<Sex> sex = userServices.findNotDeletedUserSex();
        List<Status> status = userServices.findNotDeletedUserStatus();
        List<Unit> unit = unitService.findNotDeletedUnit();
        List<Auth> auth = authService.findNotDeletedAuth();
        model.addAttribute("sex",sex);
        model.addAttribute("status",status);
        model.addAttribute("units",unit);
        model.addAttribute("auth",auth);
        //返回users页面
        return "users";
    }

    @GetMapping("/user/users")
    @ResponseBody
    public
    JSONObject users(Integer offset,Integer limit,String order,
                     Map<String,Object> map){
        List<User> allUsersButNotDeleted = userServices.
                findAllByIsDeleted( offset, limit, order);
        map.put("rows",allUsersButNotDeleted);
        map.put("total",allUsersButNotDeleted.size());
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject;
    }

    @GetMapping("/user/user/{id}")
    @ResponseBody
    public JSONObject getUser(@PathVariable(name = "id") Integer id){
        Map<String,Object> map = new HashMap<>();
        map.put("rows",userServices.findUserById(id));
        return new JSONObject(map);
    }

    // 修改
    @PutMapping("/user/user/{id}")
    @ResponseBody
    public JSONObject putUser(@PathVariable(name = "id") Integer id,String name, String email,
                              @RequestParam(required = false) int sex,@RequestParam(required = false) int status,
                              @RequestParam(value = "auth[]",required = false) int[] auth){
        Map<String,Object> map = new HashMap<>();
        User user = userServices.updateUser(id, name, email, sex, status, auth);
        map.put("user",user);
        return new JSONObject(map);

    }

    // 用户管理 结束


}

