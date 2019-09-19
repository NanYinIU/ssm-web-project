package com.nanyin.services.impl;

import com.nanyin.config.util.CommonUtil;
import com.nanyin.config.util.HttpsUtil;
import com.nanyin.entity.*;
import com.nanyin.entity.DTO.UserDto;
import com.nanyin.entity.DTO.UserInfoDto;
import com.nanyin.enumEntity.DeletedStatusEnum;
import com.nanyin.repository.AuthRepository;
import com.nanyin.repository.SexRepository;
import com.nanyin.repository.StatusRepository;
import com.nanyin.repository.UserRepository;
import com.nanyin.services.UserServices;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
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
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@CacheConfig(cacheManager = "TtlCacheManager")
public class UserServicesImpl implements UserServices {

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
        u = transferToUser(u, userInfoDto);
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
        return transferToUser(new User(), user);
    }

    private User transferToUser(User user, UserDto userDto) {
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getId() != 0) {
            user.setId(userDto.getId());
        }
        if (userDto.getPassword() != null) {
            user.setPassword(userDto.getPassword());
        }
        if (userDto.getEmail() != null) {
            user.setEmail(userDto.getEmail());
        }
        if (userDto.getAge() != 0) {
            user.setAge(userDto.getAge());
        }
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

    private User transferToUser(User user, UserInfoDto userInfoDto) {
        if (userInfoDto.getName() != null) {
            user.setName(userInfoDto.getName());
        }
        if (userInfoDto.getId() != 0) {
            user.setId(userInfoDto.getId());
        }
        if (userInfoDto.getPassword() != null) {
            user.setPassword(userInfoDto.getPassword());
        }
        if (userInfoDto.getEmail() != null) {
            user.setEmail(userInfoDto.getEmail());
        }
        if (userInfoDto.getAge() != 0) {
            user.setAge(userInfoDto.getAge());
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
    @CacheEvict(value = "users", key = "#id")
    public void deleteUser(Integer id) throws Exception {
        userRepository.deleteById(id);
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
        User user = new User(id);
        user.setPassword(generatePassword(1));
        userRepository.saveAndFlush(user);
    }

    /**
     * 使用固定的md5 1024次加密获得加密后的密码
     * @param crdentials
     * @return
     */
    private String generatePassword(Object crdentials){
        String hashAlgorithmName = "MD5";//加密方式
        Object salt = "1";//盐值
        int hashIterations = 1024;//加密1024次
        Object result = new SimpleHash(hashAlgorithmName,crdentials,salt,hashIterations);
        return result.toString();
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



}
