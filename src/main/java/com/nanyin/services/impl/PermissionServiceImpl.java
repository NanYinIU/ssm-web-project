package com.nanyin.services.impl;

import com.nanyin.config.util.PageHelper;
import com.nanyin.entity.DTO.PageQueryParams;
import com.nanyin.entity.Permission;
import com.nanyin.entity.QPermission;
import com.nanyin.repository.PermissionRepository;
import com.nanyin.services.PermissionService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Override
    public Map<String, List<Permission>> getRolePermission(Integer role) throws Exception {
        Map<String,List<Permission>> map = new HashMap<>();
        List<Permission> permissions = doGetRolePermission(role);
        map.put("cur",permissions);
        map.put("otr",permissionRepository.findAll());
        return map;
    }

    private List<Permission> doGetRolePermission(Integer role){
        QPermission permission = QPermission.permission;
        com.querydsl.core.types.Predicate predicate = permission.isNotNull().or(permission.isNull());
        predicate = role == null ? predicate : ExpressionUtils.and(predicate, permission.roles.any().id.in(role));
        return jpaQueryFactory.selectFrom(permission).where(predicate).fetch();
    }

    @Override
    public Page<Permission> findPermissions(PageQueryParams pageQueryParams) throws Exception {
        Integer offset = pageQueryParams.getOffset();
        Integer limit = pageQueryParams.getLimit();
        String search = pageQueryParams.getSearch();
        Integer roleId = pageQueryParams.getRole();
        PageRequest pageRequest = PageHelper.generatePageRequest(offset, limit);
        QPermission permission = QPermission.permission;
        Predicate predicate = permission.isNotNull().or(permission.isNull());
        predicate = search == null ? predicate : ExpressionUtils.and(predicate,permission.name.like("%"+search+"%"));
        predicate = roleId == null ? predicate : ExpressionUtils.and(predicate,permission.roles.any().id.in(roleId));
        return permissionRepository.findAll(predicate,pageRequest);
    }
}
