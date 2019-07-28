package com.nanyin.repository;

import com.nanyin.entity.ProjectStatus;
import com.nanyin.entity.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface ProjectTypeRepository extends JpaRepository<ProjectType,Integer> {
    Set<ProjectType> findByOrderByOrdAsc();
}
