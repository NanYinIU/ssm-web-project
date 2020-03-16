package com.nanyin.services;

import com.nanyin.entity.DTO.PageQueryParams;
import com.nanyin.entity.Permission;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface PermissionService {
    Page<Permission> findPermissions(PageQueryParams pageQueryParams) throws Exception;

    Map<String, List<Permission>> getRolePermission(Integer role) throws Exception;
}
