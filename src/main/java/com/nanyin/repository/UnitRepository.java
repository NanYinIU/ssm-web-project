package com.nanyin.repository;

import com.nanyin.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<Unit,Integer>,QuerydslPredicateExecutor<Unit> {
    List<Unit> findAllByParentIsNull();
}
