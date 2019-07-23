package com.nanyin.services.impl;

import com.nanyin.entity.User;
import com.nanyin.repository.UserRepository;
import com.nanyin.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
    @CacheEvict("getUserFromUserName")
    public void deleteGetUserFromUserNameCache() {
        // 登出的时候清空getUserFromUserName缓存
    }
}
