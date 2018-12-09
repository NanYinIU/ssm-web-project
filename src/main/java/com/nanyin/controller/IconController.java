package com.nanyin.controller;

import com.nanyin.common.util.LayJson;
import com.nanyin.entity.icon.Icon;
import com.nanyin.services.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @Auther: NanYin
 * @Date: 12/8/18 17:50
 * @Description:
 */
@Controller
@RequestMapping("/icon")
public class IconController {

    @Autowired
    IconService iconService;

    @RequestMapping(value = "/iconInfo",method = RequestMethod.GET)
    public @ResponseBody
    Map<Object,Object> iconInfo(@RequestParam(name = "name",required = false) String name){
        LayJson.setData((List)iconService.findIconInfo(name));
        return LayJson.Map();
    }

    @RequestMapping(value = "/icon",method = RequestMethod.PUT)
    public @ResponseBody Boolean addIcon(Icon icon){
        return iconService.addIcon(icon);
    }

    @RequestMapping(value = "/iconManage",method = RequestMethod.GET)
    public String iconManagePage(){
        return "/WEB-INF/jsp/admin/icon/iconManage.jsp";
    }

    @RequestMapping(value = "/addIconPage",method = RequestMethod.GET)
    public String addIconPage(){
        return "/WEB-INF/jsp/admin/icon/addIcon.jsp";
    }

    @RequestMapping(value = "/count",method = RequestMethod.GET)
    public @ResponseBody int getCountNumber(){
        return iconService.getCountNumber();
    }

}