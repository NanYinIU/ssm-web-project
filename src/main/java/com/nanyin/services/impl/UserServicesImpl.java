package com.nanyin.services.impl;

import com.nanyin.config.util.*;
import com.nanyin.entity.*;
import com.nanyin.entity.DTO.UserDto;
import com.nanyin.entity.DTO.UserInfoDto;
import com.nanyin.enumEntity.DeletedStatusEnum;
import com.nanyin.repository.AuthRepository;
import com.nanyin.repository.SexRepository;
import com.nanyin.repository.StatusRepository;
import com.nanyin.repository.UserRepository;
import com.nanyin.services.UserServices;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

@Service
@CacheConfig(cacheManager = "TtlCacheManager")
public class UserServicesImpl implements UserServices {

    private static final String ALGORITHM_NAME = "MD5";
    private static final int ITERATIONS = 1024;

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthRepository authRepository;

    @Autowired
    MessageSource messageSource;

    @Override
    @Cacheable(value = "user", key = "#name")
    public User getUserFromUserName(String name) {
        return userRepository.findUserByName(name);
    }

    @Cacheable(value = "users", key = "#offset+'_'+#limit+'_'+#order+'_'+#search")
    @Override
    public List<User> findAllByIsDeleted(Integer offset, Integer limit, String order, String search) throws Exception {
        logger.info("User limit:{}", limit);
        if (search == null || "".equals(search)) {
            return userRepository.findAllByIsDeleted(CommonUtil.pageRequest(offset, limit, order, "id"), (short) 0);
        }
        return userRepository.findAllByIsDeletedAndNameLike(CommonUtil.pageRequest(offset, limit, order, "id"), (short) 0, "%" + search + "%");
    }

    @Cacheable(value = "user", key = "#id")
    @Override
    public User findUserById(Integer id) throws Exception {
        return userRepository.findUsersById(id);
    }


    @Override
    @Caching(put = {
            @CachePut(value = "user", key = "#id"),
    })
    public User updateUser(Integer id, UserInfoDto userInfoDto) throws Exception {
        User u = userRepository.findUsersById(id);
        TransferBetweenUserAndUserInfoDto transferBetweenUserAndUserInfoDto = new TransferBetweenUserAndUserInfoDto();
        transferBetweenUserAndUserInfoDto.setUser(u);
        u = transferBetweenUserAndUserInfoDto.transferFrom(userInfoDto);
        return userRepository.save(u);
    }

    @Override
    @Cacheable(value = "users", key = "#search")
    public int countAllByIsDeleted(String search) throws Exception {
        if (search == null || "".equals(search)) {
            return userRepository.countAllByIsDeleted((short) 0);
        }
        return userRepository.countAllByIsDeletedAndNameLike((short) 0, "%" + search + "%");
    }

    @Override
    @CachePut(value = "user", key = "#user.id")
    public User addUser(UserDto user) throws Exception {
        TransferBetweenUserAndUserDto transferBetweenUserAndUserDto = new TransferBetweenUserAndUserDto();
        return transferBetweenUserAndUserDto.transferFrom(user);
    }


    @Override
    @CacheEvict(value = "users", key = "#id")
    public Result deleteUser(Integer id) throws Exception {
        Result result = Result.resultInstance();
        if (checkIsLastUser()) {
            result.setCode(1);
            result.setMsg("删除时最少还剩1个人！");
        } else {
            userRepository.deleteById(id);
            result.setCode(0);
            result.setMsg("删除成功");
        }
        return result;
    }

    private boolean checkIsLastUser() {
        if(userRepository.countAllByIsDeleted((short)0) == 0){
           return true ;
        }else{
            return false;
        }
    }


    @Override
    public String doLogin(String username, String password, Boolean rememberMe, String locale,
                          HttpServletRequest request, HttpServletResponse response,
                          List<Resource> sidebarInfoWapper) throws Exception{
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        Subject subject = SecurityUtils.getSubject();

        if (CommonUtil.isBlank(username)) {
            return "signin";
        }
        if (rememberMe == null) {
            rememberMe = false;
        }
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password, rememberMe);
            // 在登陆前如果想抛出自定义异常，需要在controller里面进行
            User user = getUserFromUserName(username);
            CommonUtil.setResources(messageSource);
            CommonUtil.check(CommonUtil.isNotNull(user), "username_or_password_wrong");
            CommonUtil.check(user.getStatus().getId() != -1, "user_has_been_blocked", "");
            subject.login(usernamePasswordToken);
            // 登陆成功后把 user 放到session中。把locale 信息放到 cookie中
            HttpsUtil.setAttribute("username", username);HttpsUtil.setAttribute("sidebar", sidebarInfoWapper);
            HttpsUtil.setCookie(response, "locale", locale);
            if (null != savedRequest) {
                return "redirect:" + savedRequest.getRequestUrl();
            } else {
                return "redirect:/index";
            }
        } else {
            if (null != savedRequest) {
                return "redirect:" + savedRequest.getRequestUrl().substring(8);
            } else {
                return "redirect:/index";
            }
        }

    }

    @Override
    public void changePassword(Integer id) {
        User user = userRepository.findUsersById(id);
        user.setPassword(generatePassword(1));
        userRepository.saveAndFlush(user);
    }

    /**
     * 使用固定的md5 1024次加密获得加密后的密码
     * 在使用SimpleHash进行加密Object的时候，需要重写Simplehash中的objectToBytes方法。
     * @param crdentials
     * @return
     */
    private String generatePassword(Object crdentials){
        Object salt = "1";//盐值
        BasicHash simpleHash = new BasicHash(ALGORITHM_NAME, crdentials, salt,ITERATIONS);
        return new String(simpleHash.getBytes());
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

    @Cacheable(value = "user-status")
    @Override
    public List<Status> findNotDeletedUserStatus() throws Exception {
        return statusRepository.findAllByIsDeletedOrderByOrd(DeletedStatusEnum.IS_NOT_DELETED.isJudge());
    }
    @Getter
    @Setter
    class TransferBetweenUserAndUserInfoDto extends Copyable<User,UserInfoDto> {

        private User user;
        private UserInfoDto userInfoDto;

       @Override
        protected User transferFrom(UserInfoDto userInfoDto) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            // 使用propertyUtil进行属性的拷贝
            PropertyUtils.copyProperties(user,userInfoDto);
            //其他特殊属性需要进行手动赋值
            if (userInfoDto.getPosition() != null && user.getPerson() != null) {
                user.getPerson().setPosition(userInfoDto.getPosition());
            }
            if(userInfoDto.getAddress() !=null && userInfoDto.getAddress() !=null){
                user.getPerson().setAddress(userInfoDto.getAddress());
            }
            if(userInfoDto.getName() !=null && userInfoDto.getName()!= null){
                user.getPerson().setName(userInfoDto.getName());
            }
            if (userInfoDto.getSex() != 0) {
                user.setSex(sexRepository.getOne(userInfoDto.getSex()));
            }
            if (userInfoDto.getPosition() != null && user.getPerson() != null) {
                user.getPerson().setPosition(userInfoDto.getPosition());
            }
            return user;
        }

        @Override
        protected UserInfoDto reverseTransfer(User user) {
            return null;
        }
    }

    @Getter
    @Setter
    class TransferBetweenUserAndUserDto extends Copyable<User,UserDto>{

        private User user;
        private UserDto userDto;

        @Override
        protected User transferFrom(UserDto userDto) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            PropertyUtils.copyProperties(user,userDto);
            if (userDto.getSex() != 0) {
                user.setSex(sexRepository.getOne(userDto.getSex()));
            }
            if (userDto.getStatus() != 0) {
                user.setStatus(statusRepository.getOne(userDto.getStatus()));
            }
            if (userDto.getAuths() != null) {
                Set<Auth> allByIdContains = authRepository.findDistinctByIdIn((userDto.getAuths()));
                user.setAuths(allByIdContains);
            }
            return user;
        }

        @Override
        protected UserDto reverseTransfer(User user) {
            return null;
        }
    }

}
