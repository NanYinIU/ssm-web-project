package com.nanyin.services;

import com.nanyin.entity.Sex;
import com.nanyin.entity.Status;
import com.nanyin.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserServices {

    User getUserFromUserName(String name) throws Exception;


    Page<User> findUsers(Integer offset, Integer limit, String order, String search, Integer status, Integer sex, Integer role) throws Exception;

    String login(String username, String password, Boolean rememberMe) throws Exception;

    User getCurrentUserInfo(String token) throws Exception;

    List<Sex> getStandardSex() throws Exception;

    List<Status> getStandardStatus() throws Exception;

    String logout(String token) throws Exception;

    User saveUser(User user) throws Exception;

    User updateUser(User user) throws Exception;

    void deleteUser(Integer id) throws Exception;

    User findUserByName(String name);

    Map<String,List<User>> getRolePerson(Integer role) throws Exception;

    /**
     * 返回Page的User数据
     * @param id
     * @param search
     * @param offset
     * @param limit
     * @param sort
     * @return
     */
    Page<User> findUnitUsers(Integer id, String search, Integer offset, Integer limit, String sort);

}
