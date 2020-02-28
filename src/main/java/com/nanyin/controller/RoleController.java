package com.nanyin.controller;

import com.nanyin.config.util.Result;
import com.nanyin.entity.Role;
import com.nanyin.services.RoleService;
import com.nanyin.services.UserServices;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    UserServices userServices;
    /**
     * 查询角色列表
     * @param search
     * @param offset
     * @param limit
     * @param sort
     */
    @GetMapping("/roles")
    @ApiOperation(value = "获取角色信息列表")
    public Result roleLists(String search, Integer offset, Integer limit, String sort) throws Exception{
        return new Result<>(roleService.findRoles(search,offset,limit,sort));
    }

    @PostMapping("/role")
    @ApiOperation(value = "添加角色")
    public Result saveRole(@RequestBody Role role) throws Exception{
        return new Result<>(roleService.saveRole(role));
    }

    @GetMapping("/role/{id}")
    @ApiOperation(value = "根据标识获取角色")
    public Result getRole(@PathVariable Integer id) throws Exception{
        return new Result<>(roleService.getRole(id));
    }

    @PutMapping("/role/{id}")
    @ApiOperation(value = "更新角色信息")
    public Result updateRole(@RequestBody Role role,@PathVariable Integer id) throws Exception{
        return new Result<>(roleService.updateRole(role));
    }

    @DeleteMapping("/role/{id}")
    @ApiOperation(value = "删除角色")
    public Result deleteRole(@PathVariable Integer id) throws Exception{
        roleService.deleteRole(id);
        return new Result<>();
    }

    @PostMapping("/role/users")
    @ApiOperation(value = "为角色添加人员")
    public Result addPerson() throws Exception{
        return new Result<>();
    }

    @DeleteMapping("/role/users")
    @ApiOperation(value = "为角色取消人员")
    public Result cancelPerson() throws Exception{
        return new Result<>();
    }

    @GetMapping("/role/users")
    public Result getRolePerson(Integer role) throws Exception{
        return new Result<>(userServices.getRolePerson(role));
    }
}
