package com.nanyin.controller;

import com.nanyin.entity.vo.NavBarVo;
import com.nanyin.services.NavBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    List<NavBarVo> findNavBarByUserId(Integer userId){
        return navBarService.findNavTree(1);
    }

    @RequestMapping(value = "/main",method = RequestMethod.GET)
    public String main(){
        return "/WEB-INF/jsp/admin/main.jsp";
    }

    @RequestMapping(value = "/userManage",method = RequestMethod.GET)
    public String userManage(){
        return "/WEB-INF/jsp/admin/userManage.jsp";
    }

}