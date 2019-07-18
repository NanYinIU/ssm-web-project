package com.nanyin.mapper;

import com.nanyin.entity.user.User;

import java.util.List;
import java.util.Set;

/**
 * Created by NanYin on 2017-07-08 下午5:50.
 * 包名： com.nanyin.mapper
 * 类描述：
 */
public interface UserMapper {
    User findUserById(int id);

    User findUserByName(String name);

    User selectByName(String name);




}
