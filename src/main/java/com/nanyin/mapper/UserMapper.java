package com.nanyin.mapper;

import com.nanyin.entity.User;

import java.util.List;
import java.util.Set;

/**
 * Created by NanYin on 2017-07-08 下午5:50.
 * 包名： com.nanyin.mapper
 * 类描述：
 */
public interface UserMapper {
    public List<User> selectById(int id);

    List<User> displayUser();

    User selectByName(String name);

    Set<String> getRoles(String name);

    Set<String> getPermission(String name);

    int insertUser(User user);

    int updateThisUser(User user);


}
