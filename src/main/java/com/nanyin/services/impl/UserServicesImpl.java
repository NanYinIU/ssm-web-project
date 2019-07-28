package com.nanyin.services.impl;

import com.nanyin.config.util.CommonUtil;
import com.nanyin.entity.Auth;
import com.nanyin.entity.Sex;
import com.nanyin.entity.Status;
import com.nanyin.entity.User;
import com.nanyin.enumEntity.DeletedStatusEnum;
import com.nanyin.repository.AuthRepository;
import com.nanyin.repository.SexRepository;
import com.nanyin.repository.StatusRepository;
import com.nanyin.repository.UserRepository;
import com.nanyin.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthRepository authRepository;


    @Override
    public User getUserFromUserName(String name) {
        return userRepository.findUserByName(name);
    }


    @Override
    public List<User> findAllByIsDeleted(Integer offset, Integer limit, String order) {
        return userRepository.findAllByIsDeleted(new PageRequest(offset,limit, CommonUtil.cending(order,"id")),(short)0);
    }

//    @Cacheable("findUserById")
    @Override
    public User findUserById(Integer id) {
        return userRepository.findUsersById(id);
    }


    @Override
    public User updateUser(Integer id, String name, String email, int sex, int status, int[] auth) {
        User u = userRepository.findUsersById(id);
        u.setName(name);
        u.setEmail(email);
        Sex s = sexRepository.getOne(sex);
        u.setSex(s);
        Status sta = statusRepository.getOne(status);
        u.setStatus(sta);
        Set<User> users = new HashSet<>();
        users.add(u);
        Set<Auth> allByIdContains = authRepository.findDistinctByIdIn(auth);
        u.getAuths().addAll(allByIdContains);
        allByIdContains.forEach(i -> i.setUsers(users));
        return userRepository.save(u);
    }


    /*
     * 以下-------用户相关信息--------
     *
     **/

    @Autowired
    SexRepository sexRepository;

    @Cacheable("findNotDeletedUserSex")
    @Override
    public List<Sex> findNotDeletedUserSex() {
        return sexRepository.findAllByIsDeletedOrderByOrd(DeletedStatusEnum.IS_NOT_DELETED.isJudge());
    }
    @Autowired
    StatusRepository statusRepository;

    @Cacheable("findNotDeletedUserStatus")
    @Override
    public List<Status> findNotDeletedUserStatus() {
        return statusRepository.findAllByIsDeletedOrderByOrd(DeletedStatusEnum.IS_NOT_DELETED.isJudge());
    }


}
