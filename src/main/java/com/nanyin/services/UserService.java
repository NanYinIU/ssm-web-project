package com.nanyin.services;

import com.nanyin.entity.User;

import java.util.List;
import java.util.Set;


/**
 * Created by NanYin on 2017-07-08 下午6:02.
 * 包名： com.nanyin.services
 * 类描述：
 */
public interface UserService {
    List<User> selectById(int id);

    User selectByName(String name);

    Set<String> getRoles(String username);

    Set<String> getPermissions(String username);

    List<User> displayUser();

    int insertUser(User user);

    int updateThisUser(User user);
}
