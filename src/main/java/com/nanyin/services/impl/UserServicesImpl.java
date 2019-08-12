package com.nanyin.services.impl;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.nanyin.config.util.CommonUtil;
import com.nanyin.entity.Auth;
import com.nanyin.entity.Sex;
import com.nanyin.entity.Status;
import com.nanyin.entity.User;
import com.nanyin.entity.dto.UserDto;
import com.nanyin.entity.dto.UserInfoDto;
import com.nanyin.enumEntity.DeletedStatusEnum;
import com.nanyin.repository.AuthRepository;
import com.nanyin.repository.SexRepository;
import com.nanyin.repository.StatusRepository;
import com.nanyin.repository.UserRepository;
import com.nanyin.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

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


//    @Cacheable(value = "users",key = "#")
    @Override
    public List<User> findAllByIsDeleted(Integer offset, Integer limit, String order, String search) throws Exception {
        if(search == null || "".equals(search)){
            return userRepository.findAllByIsDeleted(CommonUtil.pageRequest(offset,limit,order,"id"),(short)0);
        }
        return userRepository.findAllByIsDeletedAndNameLike(CommonUtil.pageRequest(offset,limit,order,"id"),(short)0,"%"+search+"%");
    }

    @Cacheable(value = "user",key = "#id")
    @Override
    public User findUserById(Integer id) throws Exception {
        return userRepository.findUsersById(id);
    }


    @Override
    @Caching(evict = {
            @CacheEvict(value="user", key="#id"),
//            @CacheEvict(value = "users",key = "#")
    })
    public User updateUser(Integer id, UserInfoDto userInfoDto) throws Exception {
        User u = userRepository.findUsersById(id);

        return userRepository.save(u);
    }

    @Override
    public int countAllByIsDeleted(String search) throws Exception {
        if(search == null || "".equals(search)){
            return userRepository.countAllByIsDeleted((short)0);
        }
        return userRepository.countAllByIsDeletedAndNameLike((short)0,"%"+search+"%");
    }

    @Override
    public User addUser(UserDto user) throws Exception {
        return transferToUser(new User(),user);
    }

    private User transferToUser(User user,UserDto userDto){
        user.setName(userDto.getName());
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());
        user.setAge(userDto.getAge());
        user.setSex(sexRepository.getOne(userDto.getSex()));
//        user.setUnit(u.getUnit());
        user.setStatus(statusRepository.getOne(userDto.getStatus()));
        if(userDto.getAuths() != null){
            Set<Auth> allByIdContains =  authRepository.findDistinctByIdIn((userDto.getAuths()));
            user.setAuths(allByIdContains);
        }
        return user;
    }

    private User transferToUser(User user, UserInfoDto userInfoDto){
        if(userInfoDto.getEmail() !=null){
            user.setEmail(userInfoDto.getEmail());
        }
        user.setAge(userInfoDto.getAge());

        return user;
    }

    @Override
    public void deleteUser(Integer id) throws Exception {
        userRepository.deleteById(id);
    }

    private @Validated User setUserAttributes(User u, String name, String email, int sex, int status, int[] auth){
        u.setName(name);
        u.setEmail(email);
        Sex s = sexRepository.getOne(sex);
        u.setSex(s);
        Status sta = statusRepository.getOne(status);
        u.setStatus(sta);
        Set<Auth> allByIdContains = new HashSet<>();
        if(auth !=null){
            allByIdContains =  authRepository.findDistinctByIdIn(auth);
            u.setAuths(allByIdContains);
        }
        return u;
    }
    /*
     * 以下-------用户相关信息--------
     *
     **/

    @Autowired
    SexRepository sexRepository;

    @Cacheable(value = "user-sex")
    @Override
    public List<Sex> findNotDeletedUserSex() throws Exception {
        return sexRepository.findAllByIsDeletedOrderByOrd(DeletedStatusEnum.IS_NOT_DELETED.isJudge());
    }
    @Autowired
    StatusRepository statusRepository;

    @Cacheable(value="user-status")
    @Override
    public List<Status> findNotDeletedUserStatus()  throws Exception {
        return statusRepository.findAllByIsDeletedOrderByOrd(DeletedStatusEnum.IS_NOT_DELETED.isJudge());
    }


}
