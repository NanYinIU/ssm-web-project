package com.nanyin.services;

import com.nanyin.entity.user.User;

import java.util.List;
import java.util.Set;


/**
 * Created by NanYin on 2017-07-08 下午6:02.
 * 包名： com.nanyin.services
 * 类描述：
 */
public interface UserService {
    User findUserById(int id);

    User findUserByName(String name);



}
