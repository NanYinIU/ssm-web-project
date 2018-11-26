package com.nanyin.controller;

import com.nanyin.entity.NavBar;
import com.nanyin.services.NavBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Auther: NanYin
 * @Date: 11/20/18 20:57
 * @Description: admin 页面中内容
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    NavBarService navBarService;

    @RequestMapping(value = "/navBar",method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> findNavBarByUserId(Integer userId){
        return navBarService.findNavTree(1);
    }

}