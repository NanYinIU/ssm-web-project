package com.nanyin.services.impl;

import com.nanyin.entity.NavBar;
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

    public Map<String,Object> findNavTree(Integer userId) {
        Map<String,Object> navBarMap = new HashMap<String,Object>();


        List<Map> NavTreeList = new LinkedList<Map>();

        List<NavBar> parentNodes = navBarMapper.findParentNode(userId);
        for (int i = 0; i < parentNodes.size(); i++) {
            List<NavBar> temp = new LinkedList<NavBar>();
            Map<String,Object> subBarMap = new HashMap<String, Object>();

            temp.add(parentNodes.get(i));
            Map<String,Integer> map = new HashMap<String, Integer>();
            map.put("parentId",parentNodes.get(i).getId());
            map.put("userId",userId);
            temp.addAll(navBarMapper.findChildNode(map));
            subBarMap.put("sList",temp);

            NavTreeList.add(subBarMap);
        }
        navBarMap.put("pList",NavTreeList);
        return navBarMap;
    }


}