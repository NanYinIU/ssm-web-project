package com.nanyin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanyin.common.annotation.Log;
import com.nanyin.entity.User;
import com.nanyin.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * Created by NanYin on 2017-07-08 下午6:14.
 * 包名： com.nanyin.controller
 * 类描述：关于用户的信息的操作
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService ;

//  登录页 login
    @RequestMapping("/login")
//    通过登录页面传来 name 和 password
    @Log(operationType = "select操作",operationName = "用户登录")
    public String login(String name, String password , HttpServletRequest request ){

            org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
            String pass = null;
            User user = userService.selectByName(name);
            if(user!=null) {
                String salt = user.getSalt();
                pass = new Md5Hash(password, salt, 1024).toString();
            }
            try {
            UsernamePasswordToken token = new UsernamePasswordToken(name,pass);

            subject.login(token);
//            基于shiro的session控制
            Session session =  subject.getSession();
            session.setAttribute("user",name);
            return "/static/fondPage/index.jsp";
        }catch (Exception e){
            request.setAttribute("error","用户名或密码错误");
            return "/static/fondPage/login.jsp";
        }

    }


    @RequestMapping("/logout")
    @Log(operationType = "select操作",operationName = "用户登出")
    public String logout(HttpServletRequest request){
//          清除session
        request.getSession().invalidate();
        return "/static/fondPage/login.jsp" ;
    }

    @RequestMapping("/Users/{firstpage}")
    public String Users(HttpServletRequest request, @PathVariable("firstpage")Integer firstpage){
        PageHelper.startPage(firstpage,10);
        List<User> userList = userService.displayUser();
        PageInfo<User> pageInfo = new PageInfo<User>(userList);
        request.setAttribute("Users",userList);
        request.setAttribute("page",pageInfo);
        return "/static/fondPage/Users.jsp";
    }
//    返回主页
    @RequestMapping("/returnIndex")
    public String returnIndex(){
        return "/static/fondPage/index.jsp";
    }


//    取到当前账户的信息 在通过value值回传给前台页面 这个是返回一个修改的页面

    @RequestMapping("/selectByName")
    @Log(operationType = "select操作",operationName = "展示个人信息")
    public ModelAndView selectByName(HttpSession session){
      String name = (String) session.getAttribute("user");
      User user = userService.selectByName(name);
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("userMessage",user);

      modelAndView.setViewName("/static/fondPage/Users/UpdateUser.jsp");
      return modelAndView;
    }

//   修改当前用户的基本信息
    @RequestMapping("/updateThisUser")
    public @ResponseBody
    int updateThisUser(@RequestBody User[] user){
        return userService.updateThisUser(user[0]);
    }

}
