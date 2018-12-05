package com.nanyin.services;

import com.nanyin.entity.Role;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by NanYin on 2017-07-11 上午10:01.
 * 包名： com.nanyin.services
 * 类描述：
 */
public interface RoleService {
    Set<String> getRoles(String username);
}
