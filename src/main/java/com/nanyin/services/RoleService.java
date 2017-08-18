package com.nanyin.services;

import com.nanyin.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-07-11 上午10:01.
 * 包名： com.nanyin.services
 * 类描述：
 */
public interface RoleService {
    List<Role> Roles();

    Map<String,Object> selectRoles(Map<String,Object> map);

    int delectRole(List<Role> list);

    int UpdateRole(Role role);

    int insertRole(Role role);
}
