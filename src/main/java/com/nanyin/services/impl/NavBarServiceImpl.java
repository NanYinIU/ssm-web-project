package com.nanyin.services.impl;

import com.nanyin.entity.navBar.NavBar;
import com.nanyin.entity.navBar.NavBarCategory;
import com.nanyin.entity.navBar.RNavCategoryUser;
import com.nanyin.entity.navBar.vo.NavBarCategoryInfos;
import com.nanyin.entity.navBar.vo.NavBarInfos;
import com.nanyin.entity.navBar.vo.NavBarVo;
import com.nanyin.entity.user.User;
import com.nanyin.mapper.NavBarMapper;
import com.nanyin.services.NavBarService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: NanYin
 * @Date: 11/20/18 20:58
 * @Description:
 */
public class NavBarServiceImpl implements NavBarService {

    @Autowired
    NavBarMapper navBarMapper;

    public List<NavBarInfos> findNavBarByUserId(Integer userId,Integer categoryId) {
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("userId",userId);
        param.put("categoryId",categoryId);
        return navBarMapper.findNavBarByUserId(param);
    }

    public List<NavBarCategoryInfos> findCategoryByUserId(Integer userId) {
        return navBarMapper.findCategoryByUserId(userId);
    }

    public List<NavBarCategoryInfos> findOneLevelBarByUserId(Integer userId) {
        return navBarMapper.findOneLevelBarByUserId(userId);
    }

    public List<NavBarVo> findNavTree(Integer userId,Integer categoryId) {
        List<NavBarVo> navTree = new LinkedList<NavBarVo>();
        Map<String,Object> param = new HashMap<String, Object>();
        param.put("userId",userId);
        param.put("categoryId",categoryId);
//        父节点
        List<NavBarInfos> parentList = navBarMapper.findParentNode(param);
        for (NavBarInfos n:parentList
             ) {
            NavBarVo parentNode = new NavBarVo();
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("parentId",n.getId());
            map.put("userId",userId);
            map.put("categoryId",categoryId);
            List<NavBarInfos> childNode = navBarMapper.findChildNode(map);
            parentNode.setId(n.getId());
            parentNode.setHref(n.getHref());
            parentNode.setIcon(n.getIcon());
            parentNode.setName(n.getName());
            parentNode.setSpread(n.getSpread());
            parentNode.setChildren(childNode);
            navTree.add(parentNode);
        }
        return navTree;
    }

    public void deleteOneLevelBarByUserId(Integer userId,Integer categoryId){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userId",userId);
        map.put("categoryId",categoryId);
        navBarMapper.deleteOneLevelBarByUserId(map);
    }

    public boolean checkOneLevelBarIsUsed(Integer categoryId) {
        Integer count = navBarMapper.checkOneLevelBarIsUsed(categoryId);
        return (count > 0);
    }

    public void deleteNavCategoryById(Integer categoryId){
        navBarMapper.deleteNavCategoryById(categoryId);
    }

    public Boolean insertSNavCategory(NavBarCategory navBarCategory) {
        navBarMapper.insertSNavCategory(navBarCategory);
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");

        RNavCategoryUser rNavCategoryUser = new RNavCategoryUser();
        rNavCategoryUser.setUserId(user.getId());
        rNavCategoryUser.setNavCategoryId(navBarCategory.getId());
        return insertRNavCategoryUser(rNavCategoryUser);
    }

    public boolean insertRNavCategoryUser(RNavCategoryUser rNavCategoryUser) {
        return navBarMapper.insertRNavCategoryUser(rNavCategoryUser)==1;
    }
}