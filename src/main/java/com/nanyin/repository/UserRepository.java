package com.nanyin.repository;

import com.nanyin.entity.User;
import com.nanyin.entity.dto.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface UserRepository extends JpaRepository<User,Integer> {
    /**
     * 根据名称查询用户
     * @Author nanyin
     * @Date 20:28 2019-07-24
     * @param name
     * @return com.nanyin.entity.User
     **/
    User findUserByName(String name);

//    @Query("select new com.nanyin.entity.dto.UserDto(" +
//            "u.id,u.name,u.email,u.age,u.sex,u.unit,u.status,u.gmtCreate,u.gmtModify,u.roles,u.auths," +
//            "u.projects,u.projectUserDuties" +
//            ") from User u where u.isDeleted=0 ")
//    Set<UserDto> findAllUsersButNotDeleted(Pageable pageable);

    List<User> findAllByIsDeleted(Pageable pageable,short isDeleted);

    User findUsersById(Integer id);

    int countAllByIsDeleted(short isDeleted);

    @Override
    User saveAndFlush(User user);

}

//
