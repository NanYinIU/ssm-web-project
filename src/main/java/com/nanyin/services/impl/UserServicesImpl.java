package com.nanyin.services.impl;

import com.google.common.base.Strings;
import com.nanyin.config.exceptions.userException.UserExistedException;
import com.nanyin.config.security.CustomAuthenticatioToken;
import com.nanyin.services.RedisService;
import com.nanyin.config.util.*;
import com.nanyin.entity.*;
import com.nanyin.repository.SexRepository;
import com.nanyin.repository.StatusRepository;
import com.nanyin.repository.UserRepository;
import com.nanyin.config.util.SecurityUtils;
import com.nanyin.services.UserServices;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheManager = "TtlCacheManager")
public class UserServicesImpl implements UserServices,UserDetailsService {

    @Value("${hash.algorithm.md5}")
    private String algorithm;

    @Value("${hash.algorithm.iterations}")
    private int iterations;

    @Value("${hash.algorithm.defalutCrdentials}")
    private String defalutCrdentials;


    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private AuthenticationManager authenticationManager;

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

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    /**
     * 继承自UserDetailsService，进行权限角色验证和Authorities的添加
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(username);
        if (user == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        Set<Role> roles = user.getRoles();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        // 系统层面只根据权限限制方法是否可执行
        for (Role role : roles) {
            grantedAuthorities.addAll(role.getPermissions()
                    .stream()
                    .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                    .collect(Collectors.toList()));
        }
        return new org.springframework.security.core.userdetails.User(username,user.getPassword(),grantedAuthorities);
    }


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
    public Page<User> findUsers(Integer offset, Integer limit, String order, String search, Integer status, Integer sex, Integer role) throws Exception {
        Sort sort = null;
        String propertie = order.substring(1);
        if (order.startsWith("-")) {
            sort = Sort.by(propertie).descending();
        } else if (order.startsWith("+")) {
            sort = Sort.by(propertie).ascending();
        }
        PageRequest pageRequest = PageHelper.generatePageRequest(offset, limit, sort);
        QUser user = QUser.user;
        com.querydsl.core.types.Predicate predicate = user.isNotNull().or(user.isNull());
        predicate = search == null ? predicate : ExpressionUtils.and(predicate, user.name.like("%" + search + "%"));
        predicate = sex == null ? predicate : ExpressionUtils.and(predicate, user.sex.id.eq(sex));
        predicate = status == null ? predicate : ExpressionUtils.and(predicate, user.status.id.eq(status));
        predicate = role == null ? predicate : ExpressionUtils.and(predicate, user.roles.any().id.eq(role));
        return userRepository.findAll(predicate, pageRequest);
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
    public String login(String username, String password, Boolean rememberMe)  throws Exception{
        MDCUtil.setUser(username);
        CustomAuthenticatioToken token = SecurityUtils.login(HttpUtils.getHttpServletRequest(), username, password, authenticationManager);
        return token.getToken();
    }

    @Override
    public User getCurrentUserInfo(String token) throws Exception{
        return userRepository.findUserByName(JwtUtil.getUsernameFromToken(token));
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
    public User findUserByName(String name) {
        // 精确查找名称，存在多个，或不存在返回null
        return userRepository.findUserByName(name);
    }

    @Override
    public Map<String,List<User>> getRolePerson(Integer role) throws Exception {
        Map<String,List<User>> map = new HashMap<>();
        List<User> users = doGetrolePerson(role, true, null);
        map.put("cur",users);
        map.put("otr",userRepository.findAll());
        return map;
    }

    @Override
    public Page<User> findUnitUsers(Integer unitId, String search, Integer offset, Integer limit, String sort) {
        QUser user = QUser.user;
        PageRequest pageRequest = PageHelper.generatePageRequest(offset, limit, sort);
        Predicate predicate = user.isNotNull().or(user.isNull());
        predicate = unitId == null ? predicate : ExpressionUtils.and(predicate,user.unit.id.eq(unitId));
        predicate = "".equals(search) ? predicate : ExpressionUtils.and(predicate,user.name.like("%"+search+"%"));
        return userRepository.findAll(predicate,pageRequest);
    }

    private List<User> doGetrolePerson(Integer role,boolean in,List<User> users){
        QUser user = QUser.user;
        Predicate predicate = user.isNotNull().or(user.isNull());
        if(in){
            predicate = role == null ? predicate : ExpressionUtils.and(predicate, user.roles.any().id.in(role));
        }else{
            predicate = role == null ? predicate : ExpressionUtils.and(predicate, user.notIn(users));
        }
        return jpaQueryFactory.selectFrom(user).where(predicate).fetch();
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
        HashHelper simpleHash = new HashHelper(algorithm, crdentials, salt,iterations);
        return new String(simpleHash.toHex());
    }

}
