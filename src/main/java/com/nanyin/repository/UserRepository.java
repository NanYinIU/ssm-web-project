package com.nanyin.repository;

import com.nanyin.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

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

//    @Query("select new com.nanyin.entity.DTO.UserDto(" +
//            "u.id,u.name,u.email,u.age,u.sex,u.unit,u.status,u.gmtCreate,u.gmtModify,u.roles,u.auths," +
//            "u.projects,u.projectUserDuties" +
//            ") from User u where u.isDeleted=0 ")
//    Set<UserDto> findAllUsersButNotDeleted(Pageable pageable);

    List<User> findAllByIsDeletedAndNameLike(Pageable pageable,short isDeleted,String search);

    List<User> findAllByIsDeleted(Pageable pageable,short isDeleted);


    User findUsersById(Integer id);

    /**
     * 使用sql查询，和mybatis类似的方式
     * @param id
     * @return
     */
    @Query(nativeQuery = true,value = "select u.* from user u where u.id=:id")
    List<User> findUsersByIdSql(@Param("id") Integer id);

    int countAllByIsDeleted(short isDeleted);

    int countAllByIsDeletedAndNameLike(short isDeleted,String search);

    @Override
    User saveAndFlush(User user);

}

//
