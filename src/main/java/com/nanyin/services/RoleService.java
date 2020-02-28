package com.nanyin.services;

import com.nanyin.entity.Role;
import com.nanyin.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role) throws Exception;

    Role getRole(Integer id) throws Exception;

    Role updateRole(Role role) throws Exception;

    void deleteRole(Integer id) throws Exception;

    Page<Role> findRoles(String search, Integer offset, Integer limit, String sort) throws Exception;

}