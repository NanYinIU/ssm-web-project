package com.nanyin.services.impl;

import com.google.common.base.Strings;
import com.nanyin.config.exceptions.TokenExpiredException;
import com.nanyin.config.exceptions.UserExistedException;
import com.nanyin.services.RedisService;
import com.nanyin.config.util.*;
import com.nanyin.entity.*;
import com.nanyin.config.enums.ResultCodeEnum;
import com.nanyin.repository.SexRepository;
import com.nanyin.repository.StatusRepository;
import com.nanyin.repository.UserRepository;
import com.nanyin.services.UserServices;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@CacheConfig(cacheManager = "TtlCacheManager")
public class UserServicesImpl implements UserServices {

    @Value("${hash.algorithm.md5}")
    private String algorithm;

    @Value("${hash.algorithm.iterations}")
    private int iterations;

    @Value("${hash.algorithm.defalutCrdentials}")
    private String defalutCrdentials;


    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    UserRepository userRepository;


    @Autowired
    MessageSource messageSource;

    @Autowired
    RedisService redisService;

    @Autowired
    SexRepository sexRepository;

    @Autowired
    StatusRepository statusRepository;


    @Override
//    @Cacheable(value = "user", key = "#name")
    public User getUserFromUserName(String name) {
        return userRepository.findUserByName(name);
    }

    /**
     * 复杂条件的JPA 查询语句，需要 实现JpaSpecificationExecutor接口
     * @param offset
     * @param limit
     * @param order
     * @param search
     * @param status
     * @param sex
     * @return
     * @throws Exception
     */
    @Override
    public Page<User> findUsers(Integer offset, Integer limit, String order, String search, Integer status, Integer sex) throws Exception {
        PageRequest pageRequest = new PageRequest(offset-1,limit);
        Specification<User> specification = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 使用list存储查询条件
                JpaHelper jpaHelper = new JpaHelper(root,criteriaQuery,criteriaBuilder);
                Predicate basePredicate = criteriaBuilder.equal(root.get("isDeleted"),0);
                jpaHelper.add(basePredicate);
                if(!Strings.isNullOrEmpty(search)){
                    Predicate searchPredicate = criteriaBuilder.like(root.get("name"),"%"+search+"%");
                    jpaHelper.add(searchPredicate);
                }
                if(sex!=null){
                    Predicate sexPredicate = criteriaBuilder.equal(root.get("sex").get("id"),sex);
                    jpaHelper.add(sexPredicate);
                }
                if(status!=null){
                    Predicate statusPredicate = criteriaBuilder.equal(root.get("status").get("id"),status);
                    jpaHelper.add(statusPredicate);
                }
                return jpaHelper.createOrder(order).query();
            }
        };
        return userRepository.findAll(specification,pageRequest);
    }

    /**
     * 登陆方法，使用shiro进行登陆的验证，随机生成token，放到cookie中（可不用），前端每次发送请求都在header中
     * 添加 X—Token 属性，后端每次拦截请求都要从属性中获取token在redis中是否存在，如果存在，延长redis中的ttl时间
     * @param username
     * @param password
     * @param rememberMe
     * @return
     * @throws IncorrectCredentialsException
     */
    @Override
    public String login(String username, String password, Boolean rememberMe)throws IncorrectCredentialsException {
        MDCUtil.setUser(username);
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password, false);
            try{
                subject.login(usernamePasswordToken);
            }catch (IncorrectCredentialsException ice){
                return ResultCodeEnum.WRONG_USERNAME_OR_PASSWORD.toString();
            }
            Cookie localCookie = HttpUtils.buildCookie("locale", "zh-cn");
            // 登陆成功，将用户信息连同生成的token放到redis中
            String token = UUID.randomUUID().toString();
            Cookie tokenCookie = HttpUtils.buildCookie("TokenKey", token);
            HttpUtils.setCookie(localCookie, tokenCookie);
            redisService.set(token, username,3600l);
            return token;
        }
        return "";
    }

    @Override
    public User getCurrentUserInfo(String token) throws Exception{
        String username = "";
        if(redisService.exists(token)){
            username = (String) redisService.get(token);
            if(CommonUtils.isNull(username)){
                throw new TokenExpiredException();
            }
        }else{
            throw new TokenExpiredException();
        }
        return userRepository.findUserByName(username);
    }

    @Override
    public List<Sex> getStandardSex() throws Exception {
       return sexRepository.findAll();
    }

    @Override
    public List<Status> getStandardStatus() throws Exception {
        return statusRepository.findAll();
    }

    @Override
    public String logout(String token) throws Exception {
        if(redisService.exists(token)){
            // 删除缓存中的token
            redisService.remove(token);
            return token;
        }
        return null;
    }

    @Override
    public User saveUser(User user) throws Exception {
        // 先查看用户名在系统中是否存在，如果存在，返回错误
        if(!Strings.isNullOrEmpty(user.getName())){
            User userByName = userRepository.findUserByName(user.getName());
            if(userByName!=null){
                throw new UserExistedException();
            }
        }
        user = initUser(user);
        // 创建用户
        return userRepository.saveAndFlush(user);
    }

    @Override
    public User updateUser(User user) throws Exception {
        Integer userId = user.getId();
        if(userId!=null){
            if(!Strings.isNullOrEmpty(user.getName())){
                User userByName = userRepository.findUserByName(user.getName());
                if(userByName!=null && userByName.getId()!=userId){
                    throw new UserExistedException();
                }
            }
        }
        // 设置更新时间
        user.setGmtModify(new Date());
        // 单独更新密码
        user.setPassword(generatePassword(user.getPassword(),user.getSalt()));
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void deleteUser(Integer id) throws Exception {
        userRepository.deleteById(id);
    }

    @Override
    public User findUserByName(String name) throws Exception {
        // 精确查找名称，存在多个，或不存在返回null
        return userRepository.findUserByName(name);
    }

    /**
     * 创建用户初始化默认属性
     * @param user
     * @return
     * @throws Exception
     */
    private User initUser(User user) throws Exception{
        String password = user.getPassword();
        String salt = user.getName();
        user.setGmtCreate(new Date());
        user.setGmtModify(new Date());
        user.setSalt(salt);
        password = generatePassword(password,salt);
        user.setPassword(password.toString());
        user.getPerson().setGmtCreate(new Date());
        user.getPerson().setGmtModify(new Date());
        Status status = new Status();
        status.setId(1);
        user.setStatus(status);
        return user;
    }
    /**
     * 使用固定的md5 1024次加密获得加密后的密码
     * 在使用SimpleHash进行加密Object的时候，需要重写Simplehash中的objectToBytes方法。
     * @param crdentials
     * @return
     */
    private String generatePassword(String crdentials,String salt) throws Exception{
        if(Strings.isNullOrEmpty(crdentials)){
            crdentials = defalutCrdentials;
        }
        HashHelper simpleHash = new HashHelper(algorithm, crdentials, ByteSource.Util.bytes(salt),iterations);
        return new String(simpleHash.toHex());
    }

}
