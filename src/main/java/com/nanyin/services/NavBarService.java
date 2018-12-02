package com.nanyin.services;

import com.nanyin.entity.navBar.NavBar;
import com.nanyin.entity.navBar.NavBarCategory;
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
    List<NavBar> findNavBarByUserId(Integer userId,Integer categoryId);

    List<NavBarVo> findNavTree(Integer userId,Integer categoryId);

    List<NavBarCategory> findCategoryByUserId(Integer userId);
}