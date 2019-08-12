package com.nanyin.services;

import com.nanyin.entity.Sex;
import com.nanyin.entity.Status;
import com.nanyin.entity.User;
import com.nanyin.entity.dto.UserDto;
import com.nanyin.entity.dto.UserInfoDto;

import java.util.List;

public interface UserServices {

    User getUserFromUserName(String name) throws Exception;


    List<User> findAllByIsDeleted(Integer offset, Integer limit, String order, String search) throws Exception;

    User findUserById(Integer id) throws Exception;

    User updateUser(Integer id, UserInfoDto userInfoDto) throws Exception;

    /*
     * 下面是用户表的标准属性信息
     **/

    List<Sex> findNotDeletedUserSex() throws Exception;

    List<Status> findNotDeletedUserStatus() throws Exception;

    int countAllByIsDeleted(String search) throws Exception;

    User addUser(UserDto user) throws Exception;

    void deleteUser(Integer id) throws Exception;
}
