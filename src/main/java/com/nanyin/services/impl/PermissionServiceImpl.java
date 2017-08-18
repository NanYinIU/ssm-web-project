package com.nanyin.services.impl;

import com.nanyin.common.select2;
import com.nanyin.entity.Permission;
import com.nanyin.mapper.PermissionMapper;
import com.nanyin.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-08-16 下午10:43.
 * 包名： com.nanyin.services.impl
 * 类描述：
 */
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;
    public List<select2> selectAllPermission() {

        List<Permission> lists = permissionMapper.selectAllPermission();
        List<select2> select2s = new ArrayList<select2>();
        for(int i = 0 ; i < lists.size() ; i ++){
            select2 select2 = new select2();
            select2.setId(lists.get(i).getId());
            select2.setText(lists.get(i).getDecript());
            select2s.add(select2);
        }
        return select2s;

    }

    public int insertPermissionById(Map<String, Object> map) {
        Integer r_id = (Integer) map.get("r_id");
        //            1.先去根据roleid 删除role_permission 表下的 roleid=‘roleid’的所有的项
        permissionMapper.delectPermissionByRoleId(r_id);
        //            2.把所有的项直接插入到表中

        return permissionMapper.insertPermissionById(map);
    }

}
