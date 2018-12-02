package com.nanyin.services.impl;

import com.nanyin.entity.user.User;
import com.nanyin.mapper.UserMapper;
import com.nanyin.services.UserService;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by NanYin on 2017-07-08 下午6:03.
 * 包名： com.nanyin.services.impl
 * 类描述：
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id) {
        return userMapper.findUserById(id);
    }

    public User findUserByName(String name) {
        return userMapper.findUserByName(name);
    }

    public Set<String> getRoles(String username) {
        return userMapper.getRoles(username);
    }

    public Set<String> getPermissions(String username) {
        return userMapper.getPermission(username);
    }


}
