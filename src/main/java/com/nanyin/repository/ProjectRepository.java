package com.nanyin.repository;

import com.nanyin.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ProjectRepository extends JpaRepository<Project,Integer> {

}
