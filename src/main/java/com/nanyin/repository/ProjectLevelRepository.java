package com.nanyin.repository;

import com.nanyin.entity.ProjectLevel;
import com.nanyin.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface ProjectLevelRepository extends JpaRepository<ProjectLevel,Integer> {
    Set<ProjectLevel> findByOrderByOrdAsc();
}