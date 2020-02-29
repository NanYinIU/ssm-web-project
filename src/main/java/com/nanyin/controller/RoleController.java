package com.nanyin.controller;

import com.nanyin.config.util.Result;
import com.nanyin.entity.DTO.TranferDto;
import com.nanyin.entity.Role;
import com.nanyin.services.RoleService;
import com.nanyin.services.UserServices;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 为角色添加/删除人员
     * @param direction 分为 left / right，其中left表示添加，right表示移除
     * @param keys 人员id数组
     * @param role 角色id
     * @return
     * @throws Exception
     */
    @PostMapping("/role/users")
    @ApiOperation(value = "为角色添加/删除人员")
    public Result movePerson(@RequestBody TranferDto tranferDto) throws Exception{
        roleService.movePerson(tranferDto);
        return new Result();
    }

    @GetMapping("/role/users")
    public Result getRolePerson(Integer role) throws Exception{
        return new Result<>(userServices.getRolePerson(role));
    }
}
