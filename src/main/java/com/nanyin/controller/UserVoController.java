package com.nanyin.controller;


import com.nanyin.services.UnitService;
import com.nanyin.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by NanYin on 2017-07-10 下午2:38.
 * 包名： com.nanyin.controller
 * 类描述：
 */
@Controller
@RequestMapping("/mes")
public class UserVoController {

    @Autowired
    private UserVoService userVoService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UnitService unitService;





}
