package com.nanyin.repository;

import com.nanyin.entity.ProjectStatus;
import com.nanyin.entity.ProjectType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProjectTypeRepository extends JpaRepository<ProjectType,Integer> {
    List<ProjectType> findByOrderByOrdAsc();
}
