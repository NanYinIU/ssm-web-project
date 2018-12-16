package com.nanyin.services;

import com.nanyin.entity.navBar.NavBar;
import com.nanyin.entity.navBar.NavBarCategory;
import com.nanyin.entity.navBar.RNavCategoryUser;
import com.nanyin.entity.navBar.vo.NavBarCategoryInfos;
import com.nanyin.entity.navBar.vo.NavBarInfos;
import com.nanyin.entity.navBar.vo.NavBarVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: NanYin
 * @Date: 11/20/18 20:58
 * @Description:
 */
@Service
public interface NavBarService {
    List<NavBarInfos> findNavBarByUserId(Integer userId,Integer categoryId);

    List<NavBarVo> findNavTree(Integer userId,Integer categoryId);

    List<NavBarCategoryInfos> findCategoryByUserId(Integer userId);

    List<NavBarCategoryInfos> findOneLevelBarByUserId(Integer userId);

    void deleteOneLevelBarByUserId(Integer userId,Integer categoryId);

    boolean checkOneLevelBarIsUsed(Integer categoryId);

    void deleteNavCategoryById(Integer categoryId);

    Boolean insertSNavCategory(NavBarCategory navBarCategory);

    boolean insertRNavCategoryUser(RNavCategoryUser rNavCategoryUser);
}