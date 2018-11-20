package com.nanyin.mapper;

import com.nanyin.entity.NavBar;

import java.util.List;

/**
 * @Auther: NanYin
 * @Date: 11/20/18 20:19
 * @Description: 系统主页中的nav bar内容
 */
public interface NavBarMapper {

    List<NavBar> findNavBarByUserId(Integer userId);
}