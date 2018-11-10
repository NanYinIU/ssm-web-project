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
    @RequestMapping("/roleTable")
    public @ResponseBody Map<String,Object> selectRoles(int pageSize,int pageNumber ,String name){
        int a=(pageNumber-1)*pageSize;
        int b=pageSize;
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("a",a);
        map.put("b",b);
        map.put("name",name);
        return roleService.selectRoles(map);
    }

    @RequestMapping(value = "/delectRole"
            ,consumes = "application/json" , method = RequestMethod.POST)
    @Log(operationType = "delect操作",operationName = "删除角色")
    public @ResponseBody int delectRole(@RequestBody List<Role> arrselections){
       return roleService.delectRole(arrselections);
    }

    @RequestMapping(value = "/UpdateRole"
            ,consumes = "application/json", method = RequestMethod.POST )
    @Log(operationType = "update操作",operationName = "更新角色")
    public @ResponseBody int updateRole(@RequestBody List<Role> role){

     return roleService.UpdateRole(role.get(0));
    }
    @RequestMapping("/rolePage")
    public String rolePage(){
        return "/static/fondPage/role/EditRoles.jsp";
    }

    @RequestMapping(value = "/validRole")
    public @ResponseBody Map<String,Boolean> validRole(@RequestParam String add_roleName){
//        动态验证
        boolean valid = true;
        List<Role> roleList = roleService.Roles();
        Map<String,Boolean> map = new HashMap<String, Boolean>();
        for(Role role : roleList){
            if(role.getName().equals(add_roleName)){
                valid = false ;
                break;
            }
        }
        map.put("valid",valid);
        return map;
    }

    @RequestMapping(value = "/insertRole"
            ,consumes = "application/json", method = RequestMethod.POST )
    public @ResponseBody int insertRole(@RequestBody Role role){
        return roleService.insertRole(role);
    }

}
