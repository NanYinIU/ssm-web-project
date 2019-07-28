package com.nanyin.services;

import com.nanyin.entity.Sex;
import com.nanyin.entity.Status;
import com.nanyin.entity.User;

import java.util.List;

public interface UserServices {

    User getUserFromUserName(String name);


    List<User> findAllByIsDeleted(Integer offset,Integer limit,String order);

    User findUserById(Integer id);

    User updateUser(Integer id,String name,String email,int sex,int status, int[] auth);

    /*
     * 下面是用户表的标准属性信息
     **/

    List<Sex> findNotDeletedUserSex();

    List<Status> findNotDeletedUserStatus();

}
