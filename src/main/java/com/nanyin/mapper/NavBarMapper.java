package com.nanyin.mapper;

import com.nanyin.entity.navBar.NavBar;
import com.nanyin.entity.navBar.NavBarCategory;
import com.nanyin.entity.navBar.RNavCategoryUser;
import com.nanyin.entity.navBar.vo.NavBarCategoryInfos;
import com.nanyin.entity.navBar.vo.NavBarInfos;

import java.util.List;
import java.util.Map;

/**
 * @Auther: NanYin
 * @Date: 11/20/18 20:19
 * @Description: 系统主页中的nav bar内容
 */
public interface NavBarMapper {

    List<NavBarInfos> findNavBarByUserId(Map<String,Object> map);

    List<NavBarInfos> findParentNode(Map<String,Object> map);

    List<NavBarInfos> findChildNode(Map<String,Object> map);

    List<NavBarCategoryInfos> findCategoryByUserId(Integer userId);

    List<NavBarCategoryInfos> findOneLevelBarByUserId(Integer userId);

    Integer deleteOneLevelBarByUserId(Map<String,Object> map);

    Integer checkOneLevelBarIsUsed(Integer categoryId);

    Integer deleteNavCategoryById(Integer categoryId);

    Integer insertSNavCategory(NavBarCategory navBarCategory);

    Integer insertRNavCategoryUser(RNavCategoryUser rNavCategoryUser);


}