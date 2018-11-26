package com.nanyin.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nanyin.common.annotation.Log;
import com.nanyin.common.util.EDSUtil;
import com.nanyin.entity.User;
import com.nanyin.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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


}
