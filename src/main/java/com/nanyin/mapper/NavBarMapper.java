package com.nanyin.mapper;

import com.nanyin.entity.navBar.NavBar;
import com.nanyin.entity.navBar.NavBarCategory;

import java.util.List;
import java.util.Map;

/**
 * @Auther: NanYin
 * @Date: 11/20/18 20:19
 * @Description: 系统主页中的nav bar内容
 */
public interface NavBarMapper {

    List<NavBar> findNavBarByUserId(Map<String,Object> map);

    List<NavBar> findParentNode(Map<String,Object> map);

    List<NavBar> findChildNode(Map<String,Object> map);

    List<NavBarCategory> findCategoryByUserId(Integer userId);

    Integer deleteOneLevelBarByUserId(Map<String,Object> map);

    Integer checkOneLevelBarIsUsed(Integer categoryId);

    Integer deleteNavCategoryById(Integer categoryId);
}