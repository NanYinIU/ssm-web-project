package com.nanyin.services.impl;

import com.nanyin.config.util.PageHelper;
import com.nanyin.entity.Permission;
import com.nanyin.repository.PermissionRepository;
import com.nanyin.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public Page<Permission> findPermissions(String search, Integer offset, Integer limit, String sort) throws Exception {
        PageRequest pageRequest = PageHelper.generatePageRequest(offset, limit);
        return permissionRepository.findAll(new Specification<Permission>() {
            @Override
            public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate p1 = search == null ? null:criteriaBuilder.like(root.get("name").as(String.class), "%"+search+"%");
                if(p1 != null){
                    query.where(criteriaBuilder.and(p1));
                }
                return query.getRestriction();
            }
        },pageRequest);
    }
}
