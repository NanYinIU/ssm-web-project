package com.nanyin.controller;

import com.nanyin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
