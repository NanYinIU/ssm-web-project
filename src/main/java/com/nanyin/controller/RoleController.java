package com.nanyin.controller;

import com.nanyin.common.annotation.Log;
import com.nanyin.entity.Role;
import com.nanyin.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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


}
