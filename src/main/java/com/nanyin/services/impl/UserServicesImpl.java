package com.nanyin.services.impl;

import com.nanyin.config.util.CommonUtil;
import com.nanyin.entity.User;
import com.nanyin.entity.dto.UserDto;
import com.nanyin.repository.UserRepository;
import com.nanyin.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    UserRepository userRepository;

    @Cacheable("getUserFromUserName")
    @Override
    public User getUserFromUserName(String name) {
        return userRepository.findUserByName(name);
    }

    @Override
    public List<UserDto> findAllUsersButNotDeleted(Integer offset,Integer limit,String order) {
        return userRepository.findAllUsersButNotDeleted(new PageRequest(offset,limit, CommonUtil.cending(order,"id")));
    }

}
