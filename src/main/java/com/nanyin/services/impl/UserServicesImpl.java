package com.nanyin.services.impl;

import com.nanyin.entity.User;
import com.nanyin.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServicesImpl implements UserServices {

    @Override
    public User getUserFromUserName(String name) {
        return null;
    }
}
