package com.nanyin.services.impl;

import com.google.common.base.Strings;
import com.nanyin.config.exceptions.TokenExpiredException;
import com.nanyin.config.redis.RedisService;
import com.nanyin.config.util.*;
import com.nanyin.entity.*;
import com.nanyin.config.enums.ResultCodeEnum;
import com.nanyin.repository.AuthRepository;
import com.nanyin.repository.SexRepository;
import com.nanyin.repository.StatusRepository;
import com.nanyin.repository.UserRepository;
import com.nanyin.services.UserServices;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
                List<Predicate> predicatesList = new ArrayList<Predicate>();
                // isdelete = 0
                Predicate basePredicate = criteriaBuilder.equal(root.get("isDeleted"),0);
                predicatesList.add(basePredicate);

                if(!Strings.isNullOrEmpty(search)){
                    Predicate searchPredicate = criteriaBuilder.like(root.get("name"),"%"+search+"%");
                    predicatesList.add(searchPredicate);
                }
                if(sex!=null){
                    Predicate sexPredicate = criteriaBuilder.equal(root.get("sex").get("id"),sex);
                    predicatesList.add(sexPredicate);
                }
                if(status!=null){
                    Predicate statusPredicate = criteriaBuilder.equal(root.get("status").get("id"),status);
                    predicatesList.add(statusPredicate);
                }

                //最终将查询条件拼好然后return
                Predicate[] predicates = new Predicate[predicatesList.size()];
                if(!Strings.isNullOrEmpty(order)){
                    if(order.length() > 1){
                        String orderName = order.substring(1);
                        if(order.startsWith("-")){
                            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(orderName)));
                        }else if(order.startsWith("+")){
                            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(orderName)));
                        }
                    }
                }
                return criteriaBuilder.and(predicatesList.toArray(predicates));
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

    /**
     * 使用固定的md5 1024次加密获得加密后的密码
     * 在使用SimpleHash进行加密Object的时候，需要重写Simplehash中的objectToBytes方法。
     * @param crdentials
     * @return
     */
    private String generatePassword(Object crdentials){
        Object salt = "1";//盐值
        HashHelper simpleHash = new HashHelper(ALGORITHM_NAME, crdentials, salt,ITERATIONS);
        return new String(simpleHash.getBytes());
    }

}
