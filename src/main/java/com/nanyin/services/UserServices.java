package com.nanyin.services;

import com.nanyin.entity.User;
import com.nanyin.entity.dto.UserDto;

import java.util.List;

public interface UserServices {
    User getUserFromUserName(String name);

    List<UserDto> findAllUsersButNotDeleted(Integer offset,Integer limit,String order);
}
