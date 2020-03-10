package com.nanyin.services;

import com.nanyin.entity.Permission;
import org.springframework.data.domain.Page;

public interface PermissionService {
    Page<Permission> findPermissions(String search, Integer offset, Integer limit, String sort) throws Exception;
}
