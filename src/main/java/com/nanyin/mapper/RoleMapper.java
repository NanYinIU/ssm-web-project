package com.nanyin.mapper;

import com.nanyin.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-07-11 上午9:45.
 * 包名： com.nanyin.mapper
 * 类描述：角色 也就是职位 的mapper
 */
public interface RoleMapper {
//    查询所有的职位名称
    List<Role> selectRoles(Map<String,Object> map);

    List<Role> Roles();

    int selectByRoleName(String describe);

    int delectRole(List<Role> list);

    int UpdateRole(Role role);

    int insertRole(Role role);
}
