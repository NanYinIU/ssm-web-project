package com.nanyin.repository;

import com.nanyin.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource,Integer> {
//    List<Resource> findResourceByAuthsByUsersNameAndType(String name,Integer type);
    List<Resource> findByAuths_Users_NameAndType_IdOrderByOrdAsc(String name, Integer type);

}
