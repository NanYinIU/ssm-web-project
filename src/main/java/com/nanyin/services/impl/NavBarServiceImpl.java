package com.nanyin.services.impl;

import com.nanyin.entity.NavBar;
import com.nanyin.mapper.NavBarMapper;
import com.nanyin.services.NavBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: NanYin
 * @Date: 11/20/18 20:58
 * @Description:
 */
public class NavBarServiceImpl implements NavBarService {

    @Autowired
    NavBarMapper navBarMapper;

    public List<NavBar> findNavBarByUserId(Integer userId) {
        return navBarMapper.findNavBarByUserId(userId);
    }
}