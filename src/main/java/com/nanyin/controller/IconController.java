package com.nanyin.controller;

import com.nanyin.common.util.LayJson;
import com.nanyin.entity.icon.Icon;
import com.nanyin.services.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/icons",method = RequestMethod.GET)
    public @ResponseBody
    LayJson iconInfo(@RequestParam(name = "name",required = false) String name){
        List Data = iconService.findIconInfo(name);
        LayJson layJson = new LayJson();
        layJson.setData(Data);
        layJson.setCount(Data.size());
        return layJson;
    }

    @RequestMapping(value = "/icon",method = RequestMethod.POST)
    public @ResponseBody Boolean addIcon(@PathVariable(value = "id",required = false) Integer id,
                                                 @RequestParam String iconName,
                                                 @RequestParam String iconUnicode,
                                                 @RequestParam String iconClass){
        Icon icon = new Icon();
        icon.setIconName(iconName);
        icon.setIconUnicode(iconUnicode);
        icon.setIconClass(iconClass);
        return iconService.addIcon(icon);
    }
    @RequestMapping(value = "/icon/{id}",method = RequestMethod.PUT)
    public @ResponseBody Boolean modifyIcon(@PathVariable(value = "id",required = false) Integer id,
                                                 @RequestParam String iconName,
                                                 @RequestParam String iconUnicode,
                                                 @RequestParam String iconClass){
        Icon icon = new Icon();
        icon.setId(id);
        icon.setIconName(iconName);
        icon.setIconUnicode(iconUnicode);
        icon.setIconClass(iconClass);
        return iconService.modifyIcon(icon);
    }

    @RequestMapping(value = "/icon/{id}",method = RequestMethod.DELETE)
    public @ResponseBody Boolean deleteIcon(@PathVariable(value = "id") int id){
        return iconService.deleteIcon(id);
    }

    @RequestMapping(value = "/iconManage",method = RequestMethod.GET)
    public String iconManagePage(){
        return "/WEB-INF/jsp/admin/icon/iconManage.jsp";
    }

    @RequestMapping(value = "/iconManage/addIconPage",method = RequestMethod.GET)
    public String addIconPage(){
        return "/WEB-INF/jsp/admin/icon/addIcon.jsp";
    }

    @RequestMapping(value = "/iconManage/modifyIconPage",method = RequestMethod.GET)
    public String modifyIconPage(){
        return "/WEB-INF/jsp/admin/icon/modifyIcon.jsp";
    }

    @RequestMapping(value = "/icon/count",method = RequestMethod.GET)
    public @ResponseBody int getCountNumber(){
        return iconService.getCountNumber();
    }

    @RequestMapping(value = "/icon/batch/{ids}",method = RequestMethod.DELETE)
    public Boolean batchDeleteIcons(@PathVariable(value = "ids",required = true) String ids){
        return iconService.deleteIcons(ids);
    }

}