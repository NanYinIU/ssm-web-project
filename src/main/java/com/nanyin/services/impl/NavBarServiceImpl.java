package com.nanyin.services.impl;

import com.nanyin.entity.NavBar;
import com.nanyin.entity.vo.NavBarVo;
import com.nanyin.mapper.NavBarMapper;
import com.nanyin.services.NavBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<NavBar> findNavBarByUserId(Integer userId) {
        return navBarMapper.findNavBarByUserId(userId);
    }

    public List<NavBarVo> findNavTree(Integer userId) {
        List<NavBarVo> navTree = new LinkedList<NavBarVo>();
//        父节点
        List<NavBar> parentList = navBarMapper.findParentNode(userId);
        for (NavBar n:parentList
             ) {
            NavBarVo parentNode = new NavBarVo();
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("parentId",n.getId());
            map.put("userId",userId);
            List<NavBar> childNode = navBarMapper.findChildNode(map);
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


}