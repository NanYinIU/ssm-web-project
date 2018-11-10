package com.nanyin.services.impl;

import com.nanyin.entity.User;
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

    public List<User> selectById(int id) {
        return userMapper.selectById(id);
    }

    public User selectByName(String name) {
        return userMapper.selectByName(name);
    }

    public Set<String> getRoles(String username) {
        return userMapper.getRoles(username);
    }

    public Set<String> getPermissions(String username) {
        return userMapper.getPermission(username);
    }

    public List<User> displayUser(){
        return userMapper.displayUser();
    }

    public int insertUser(User user) {
        Date date = new Date();
        user.setCreate_time(date);
        user.setSalt("salt");
//        提供默认的roleId 为2
        user.setUnitId(1);
        return userMapper.insertUser(user);
    }

    public int updateThisUser(User user) {
        User user1 = new User();
        user1.setId(user.getId());
        user1.setName(user.getName());
        user1.setPassword(new Md5Hash(user.getPassword(),"salt",1024).toString());
        user1.setSex(user.getSex());
        user1.setAge(user.getAge());
        return userMapper.updateThisUser(user1);
    }


}
