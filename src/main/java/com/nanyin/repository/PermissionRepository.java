package com.nanyin.repository;

import com.nanyin.entity.Permission;
import com.nanyin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission,Integer>, JpaSpecificationExecutor<Permission>, QuerydslPredicateExecutor<Permission> {
}
