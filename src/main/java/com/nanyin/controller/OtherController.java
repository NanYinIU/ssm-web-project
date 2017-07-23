package com.nanyin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by NanYin on 2017-07-16 下午5:09.
 * 包名： com.nanyin.controller
 * 类描述：
 */
@Controller
@RequestMapping("/other")
public class OtherController {

    @RequestMapping("/returnDruidPage")
    public String returnDruidPage(){
        return "/static/fondPage/monitor/DruidManager.jsp";
    }

}
