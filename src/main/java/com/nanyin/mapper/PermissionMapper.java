package com.nanyin.mapper;

import com.nanyin.entity.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by NanYin on 2017-08-16 下午10:41.
 * 包名： com.nanyin.mapper
 * 类描述：
 */
@Repository
public interface PermissionMapper {

    List<Permission> selectAllPermission();

    int insertPermissionById(Map<String,Object> map);

    int delectPermissionByRoleId(int role_id);
}
