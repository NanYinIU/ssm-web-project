package com.nanyin.repository;

import com.nanyin.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit,Integer> {

    List<Unit> findAllByIsDeletedOrderByOrd(Boolean isDeleted);
}
