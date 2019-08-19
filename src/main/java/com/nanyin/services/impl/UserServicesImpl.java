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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

@Service
@CacheConfig(cacheManager = "TtlCacheManager")
public class UserServicesImpl implements UserServices {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthRepository authRepository;


    @Override
    @Cacheable(value = "user",key = "#name")
    public User getUserFromUserName(String name) {
        return userRepository.findUserByName(name);
    }

    @Cacheable(value = "users",key = "#offset+'_'+#limit+'_'+#order+'_'+#search")
    @Override
    public List<User> findAllByIsDeleted(Integer offset, Integer limit, String order, String search) throws Exception {
        logger.info("User limit:{}",limit);
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
    @Caching(put = {
            @CachePut(value="user", key="#id"),
    })
    public User updateUser(Integer id, UserInfoDto userInfoDto) throws Exception {
        User u = userRepository.findUsersById(id);
        u = transferToUser(u,userInfoDto);
        return userRepository.save(u);
    }

    @Override
    @Cacheable(value = "users",key = "#search")
    public int countAllByIsDeleted(String search) throws Exception {
        if(search == null || "".equals(search)){
            return userRepository.countAllByIsDeleted((short)0);
        }
        return userRepository.countAllByIsDeletedAndNameLike((short)0,"%"+search+"%");
    }

    @Override
    @CachePut(value = "user",key = "#user.id")
    public User addUser(UserDto user) throws Exception {
        return transferToUser(new User(),user);
    }

    private User transferToUser(User user,UserDto userDto){
        if(userDto.getName()!=null){
            user.setName(userDto.getName());
        }
        if(userDto.getId() != 0){
            user.setId(userDto.getId());
        }
        if(userDto.getPassword()!=null){
            user.setPassword(userDto.getPassword());
        }
        if(userDto.getEmail()!=null){
            user.setEmail(userDto.getEmail());
        }
        if(userDto.getAge() !=0){
            user.setAge(userDto.getAge());
        }
        if(userDto.getSex() != 0){
            user.setSex(sexRepository.getOne(userDto.getSex()));
        }
        if(userDto.getStatus() !=0){
            user.setStatus(statusRepository.getOne(userDto.getStatus()));
        }
        if(userDto.getAuths() != null){
            Set<Auth> allByIdContains =  authRepository.findDistinctByIdIn((userDto.getAuths()));
            user.setAuths(allByIdContains);
        }
        return user;
    }

    private User transferToUser(User user, UserInfoDto userInfoDto){
        if(userInfoDto.getName()!=null){
            user.setName(userInfoDto.getName());
        }
        if(userInfoDto.getId() != 0){
            user.setId(userInfoDto.getId());
        }
        if(userInfoDto.getPassword()!=null){
            user.setPassword(userInfoDto.getPassword());
        }
        if(userInfoDto.getEmail()!=null){
            user.setEmail(userInfoDto.getEmail());
        }
        if(userInfoDto.getAge() !=0){
            user.setAge(userInfoDto.getAge());
        }
        if(userInfoDto.getSex() != 0){
            user.setSex(sexRepository.getOne(userInfoDto.getSex()));
        }
        if(userInfoDto.getPosition()!=null && user.getPerson() !=null){
            user.getPerson().setPosition(userInfoDto.getPosition());
        }
        return user;
    }

    @Override
    @CacheEvict(value = "users",key = "#id")
    public void deleteUser(Integer id) throws Exception {
        userRepository.deleteById(id);
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
