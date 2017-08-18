package com.nanyin.services.impl;

import com.nanyin.entity.Role;
import com.nanyin.mapper.RoleMapper;
import com.nanyin.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.wiring.ClassNameBeanWiringInfoResolver;
import org.springframework.stereotype.Service;

import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-07-11 上午10:01.
 * 包名： com.nanyin.services.impl
 * 类描述：
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    public List<Role> Roles() {
        return roleMapper.Roles();
    }

    public Map<String, Object> selectRoles(Map<String, Object> map) {
        Map<String,Object> result = new HashMap<String, Object>();

//        需要传入total 和 rows
        List<Role> rows = roleMapper.selectRoles(map);

        int total = roleMapper.Roles().size();

        result.put("rows",rows);
        result.put("total",total);
        return result;

    }

    public int delectRole(List<Role> role) {

        return roleMapper.delectRole(role);
    }

    public int UpdateRole(Role role) {
        return roleMapper.UpdateRole(role);

    }

    public int insertRole(Role role) {
        return roleMapper.insertRole(role);
    }
}
