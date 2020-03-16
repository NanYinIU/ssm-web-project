package com.nanyin.services;

import com.nanyin.entity.DTO.TranferDto;
import com.nanyin.entity.Role;
import org.springframework.data.domain.Page;

public interface RoleService {
    Role saveRole(Role role) throws Exception;

    Role getRole(Integer id) throws Exception;

    Role updateRole(Role role) throws Exception;

    void deleteRole(Integer id) throws Exception;

    Page<Role> findRoles(String search, Integer offset, Integer limit, String sort) throws Exception;

    /**
     * 为角色增加/删除人员
     * @param tranferDto
     * @throws Exception
     */
    void movePersons(TranferDto tranferDto)throws Exception;

    /**
     * 为角色增加/删除权限
     * @param tranferDto
     * @throws Exception
     */
    void movePermission(TranferDto tranferDto) throws Exception;
}
