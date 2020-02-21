package com.nanyin.repository;

import com.nanyin.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User> {
    /**
     * 根据名称查询用户
     * @Author nanyin
     * @Date 20:28 2019-07-24
     * @param name
     * @return com.nanyin.entity.User
     **/
    @Query(nativeQuery = true,value = "select u.* from user u where u.name=:name")
    User findUserByName(String name);

    @Override
    User saveAndFlush(User user);

}

//
