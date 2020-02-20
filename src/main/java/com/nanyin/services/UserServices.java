package com.nanyin.services;

import com.nanyin.config.util.Result;
import com.nanyin.entity.Resource;
import com.nanyin.entity.Sex;
import com.nanyin.entity.Status;
import com.nanyin.entity.User;
import com.nanyin.entity.DTO.UserDto;
import com.nanyin.entity.DTO.UserInfoDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface UserServices {

    User getUserFromUserName(String name) throws Exception;


    Result findAllByIsDeleted(Integer offset, Integer limit, String order, String search) throws Exception;

    String login(String username, String password, Boolean rememberMe);

    User getCurrentUserInfo(String token) throws Exception;
}
