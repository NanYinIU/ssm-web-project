package com.nanyin.services.impl;

import com.nanyin.entity.QRole;
import com.nanyin.entity.QUser;
import com.nanyin.entity.Role;
import com.nanyin.entity.User;
import com.nanyin.repository.RoleRepository;
import com.nanyin.services.RoleService;
import com.querydsl.core.Query;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Override
    public Role saveRole(Role role)  throws Exception{
        role.setGmtCreate(new Date());
        role.setGmtModify(new Date());
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public Role getRole(Integer id)  throws Exception {
        return roleRepository.getOne(id);
    }

    @Override
    public Role updateRole(Role role) throws Exception {
        role.setGmtModify(new Date());
        return roleRepository.saveAndFlush(role);
    }

    @Override
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }



    @Override
    public Page<Role> findRoles(String search, Integer offset, Integer limit, String order) throws Exception {
        // 获取分页
        if(offset == null || limit == null){
            offset = 1;
            limit = Integer.MAX_VALUE;
        }
        PageRequest pageRequest = PageRequest.of(offset-1,limit);
        QRole role = QRole.role;
        com.querydsl.core.types.Predicate predicate = role.isNotNull().or(role.isNull());
        predicate = search == null ? predicate : ExpressionUtils.and(predicate,role.name.like("%"+search+"%"));
        return roleRepository.findAll(predicate,pageRequest);
    }

}
