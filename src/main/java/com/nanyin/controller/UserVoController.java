package com.nanyin.controller;


import com.nanyin.common.annotation.Log;
import com.nanyin.entity.Organization;
import com.nanyin.entity.Role;

import com.nanyin.entity.user.vo.UserVo;
import com.nanyin.services.UnitService;
import com.nanyin.services.RoleService;
import com.nanyin.services.UserVoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

//      人员信息显示
    @RequestMapping(value = "/AllMessageDis")
    @Log(operationName = "信息展示",operationType = "select操作")
    public ModelAndView AllMessageDis(){
       ModelAndView modelAndView = new ModelAndView();
       List<Role> roleList = roleService.Roles();
       List<Organization> organizationList = unitService.selectOrganizations();

       modelAndView.addObject("Roles",roleList);
       modelAndView.addObject("organ",organizationList);

       modelAndView.setViewName("/static/fondPage/Users/EditUsers.jsp");
       return modelAndView;
    }

    @RequestMapping("/selectByName")
    public @ResponseBody
    Map<String,Object> selectByName(int pageSize, int pageNumber,String ByName){
        int a=(pageNumber-1)*pageSize;
        int b=pageSize;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("name",ByName);
        param.put("a", a);
        param.put("b", b);
        return userVoService.selectByName(param);
    }

    @RequestMapping(value = "/delectParam"
                    ,consumes = "application/json")
    @Log(operationType = "delect操作",operationName = "删除用户")
    public @ResponseBody int delectParam(@RequestBody  List<UserVo> list){
        return userVoService.delectParam(list);
    }

    @RequestMapping(value = "/insertUserVo"
            ,consumes = "application/json" , method = RequestMethod.POST)
    @Log(operationType = "insert操作",operationName = "新建用户")
    public @ResponseBody int insertUserVo(@RequestBody  @Validated UserVo[] role, BindingResult result) {
//        有错误不处理 有前端的控制
        if(result.hasErrors()){
            return 0 ;
        }
        return userVoService.insertUserVo(role[0]);
    }


    @RequestMapping(value = "/UpdateUserVo"
            ,consumes = "application/json" , method = RequestMethod.POST)
    @Log(operationType = "update操作",operationName = "更新用户")
    public @ResponseBody int UpdateUserVo(@RequestBody  @Validated UserVo[] postdata , BindingResult bindingResult){
        if (bindingResult.hasErrors()){
//            发生错误不处理
            return 0;
        }

        return userVoService.UpdateUserVo(postdata[0]);
    }

    @RequestMapping(value = "/validName",
            produces = "application/json;charset=UTF-8" ,method = RequestMethod.POST)
    public @ResponseBody Map<String,Boolean> validName(@RequestParam String txt_name1){
        boolean valid = true ;
        List<UserVo> userVoList = userVoService.AllMessageDis();
        for (UserVo userVo : userVoList){
            if(userVo.getName().equals(txt_name1)){
                valid = false;
                break;
            }
        }
        Map<String,Boolean> map = new HashMap<String, Boolean>();
        map.put("valid",valid);
        return map;
    }
    @RequestMapping(value = "/validNamed",
            produces = "application/json;charset=UTF-8" ,method = RequestMethod.POST)
    public @ResponseBody Map<String,Boolean> validNamed(@RequestParam String txt_name){
        boolean validd = true ;
        List<UserVo> userVoList = userVoService.AllMessageDis();
        for (UserVo userVo : userVoList){
            if(userVo.getName().equals(txt_name)){
                validd = false;
                break;

            }
        }
        Map<String,Boolean> map = new HashMap<String, Boolean>();
        map.put("valid",validd);
        return map;
    }


}
