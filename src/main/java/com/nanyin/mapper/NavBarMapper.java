package com.nanyin.mapper;

import com.nanyin.entity.NavBar;

import java.util.List;
import java.util.Map;

/**
 * @Auther: NanYin
 * @Date: 11/20/18 20:19
 * @Description: 系统主页中的nav bar内容
 */
public interface NavBarMapper {

    List<NavBar> findNavBarByUserId(Integer userId);

    List<NavBar> findParentNode(Integer userId);

    List<NavBar> findChildNode(Map<String,Integer> map);
}