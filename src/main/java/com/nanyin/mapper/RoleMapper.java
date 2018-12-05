package com.nanyin.mapper;

import com.nanyin.entity.Role;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by NanYin on 2017-07-11 上午9:45.
 * 包名： com.nanyin.mapper
 * 类描述：角色 也就是职位 的mapper
 */
public interface RoleMapper {
    Set<String> getRoles(String name);
}
