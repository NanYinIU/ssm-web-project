package com.nanyin.repository;

import com.nanyin.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface ProjectRepository extends JpaRepository<Project,Integer> {
    Set<Project> findByStatusAndTypeAndLevelOrderByOrdDesc(String status, String level, String type);
}