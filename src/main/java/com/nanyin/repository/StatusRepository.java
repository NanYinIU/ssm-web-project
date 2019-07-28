package com.nanyin.repository;

import com.nanyin.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository <Status,Integer>{
    List<Status> findAllByIsDeletedOrderByOrd(Boolean isDeleted);
}
