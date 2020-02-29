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

    void movePerson(TranferDto tranferDto)throws Exception;
}
