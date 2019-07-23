package com.nanyin.repository;

import com.nanyin.entity.Project;
import com.nanyin.entity.ProjectLevel;
import com.nanyin.entity.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProjectStatusRepository extends JpaRepository<ProjectStatus,Integer> {
    List<ProjectStatus> findByOrderByOrdAsc();
}
