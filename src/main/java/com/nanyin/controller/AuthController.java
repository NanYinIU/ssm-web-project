package com.nanyin.controller;

import com.nanyin.common.format.PermissionFormat;
import com.nanyin.common.select2;
import com.nanyin.services.AuthService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-08-16 下午11:00.
 * 包名： com.nanyin.controller
 * 类描述：
 */
@Controller
@RequestMapping("/per")
public class AuthController {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    AuthService authService;

    private List<Integer> permissionId;


}
