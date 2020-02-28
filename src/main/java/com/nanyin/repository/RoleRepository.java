package com.nanyin.repository;

import com.nanyin.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

@Component
public interface RoleRepository extends JpaRepository<Role,Integer>, JpaSpecificationExecutor<Role>, QuerydslPredicateExecutor<Role> {
}
