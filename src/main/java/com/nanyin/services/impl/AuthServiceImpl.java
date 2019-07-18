package com.nanyin.services.impl;

import com.nanyin.common.select2;
import com.nanyin.entity.Auth;
import com.nanyin.mapper.AuthMapper;
import com.nanyin.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by NanYin on 2017-08-16 下午10:43.
 * 包名： com.nanyin.services.impl
 * 类描述：
 */
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthMapper authMapper;


    public Set<String> getPermissions(String username) {
        return authMapper.getPermission(username);
    }
}
