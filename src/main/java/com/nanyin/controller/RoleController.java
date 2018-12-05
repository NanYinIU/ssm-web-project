package com.nanyin.controller;

import com.nanyin.common.annotation.Log;
import com.nanyin.entity.Role;
import com.nanyin.services.RoleService;
import com.nanyin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-07-12 上午9:04.
 * 包名： com.nanyin.controller
 * 类描述：
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;


}
