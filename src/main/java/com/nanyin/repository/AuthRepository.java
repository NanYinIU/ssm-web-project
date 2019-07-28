package com.nanyin.repository;

import com.nanyin.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface AuthRepository extends JpaRepository<Auth,Integer> {

    List<Auth> findByIsDeletedOrderByOrd(Boolean isDeleted);

    Set<Auth> findDistinctByIdIn(int[] arr);


}
