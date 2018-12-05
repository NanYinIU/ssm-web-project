package com.nanyin.services.impl;

import com.nanyin.entity.Role;
import com.nanyin.mapper.RoleMapper;
import com.nanyin.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.wiring.ClassNameBeanWiringInfoResolver;
import org.springframework.stereotype.Service;

import java.rmi.MarshalledObject;
import java.util.*;

/**
 * Created by NanYin on 2017-07-11 上午10:01.
 * 包名： com.nanyin.services.impl
 * 类描述：
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;

    public Set<String> getRoles(String username) {
        return roleMapper.getRoles(username);
    }
}
