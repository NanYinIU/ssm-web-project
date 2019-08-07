package com.nanyin.repository;

import com.nanyin.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource,Integer> {
    /**
     * 加载该用户所拥有的所有权限的资源，并且按照资源的ord进行正向排序。
     * @Author nanyin
     * @Date 09:06 2019-07-23
     * @param name 1
     * @param type 2
     * @return java.util.List<com.nanyin.entity.Resource>
     **/
    List<Resource> findByAuths_Users_NameAndType_IdOrderByOrdAsc(String name, Integer type);

}
