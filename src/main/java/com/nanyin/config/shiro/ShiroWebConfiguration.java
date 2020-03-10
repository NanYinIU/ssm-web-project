package com.nanyin.config.shiro;

import com.nanyin.config.util.MDCUtil;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.SavedRequest;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroWebConfiguration {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.timeout}")
    private int redisTimeout;

    @Value("${hash.algorithm.md5}")
    private String algorithm;

    @Value("${hash.algorithm.iterations}")
    private int iterations;

    private Logger logger = LoggerFactory.getLogger(ShiroWebConfiguration.class);
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

//        MyPassThruAuthenticationFilter myPassThruAuthenticationFilter = new MyPassThruAuthenticationFilter();
//        Map<String,Filter> filterMap = new HashMap<>();
//        filterMap.put("authc",myPassThruAuthenticationFilter);
//        shiroFilterFactoryBean.setFilters(filterMap);

        shiroFilterFactoryBean.setLoginUrl("/user/login");
//        shiroFilterFactoryBean.setSuccessUrl("/");
        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap());
//        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        return shiroFilterFactoryBean;
    }



    public Map<String, String> filterChainDefinitionMap(){
        Map<String,String> map = new HashMap<>();
        map.put("/assets/**","anon");
        map.put("/webjars/**","anon");
//        map.put("/**","authc");
        map.put("/**","anon");
        return map;
    }
    /**
     * SecurityManager 安全管理器；Shiro的核心
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(shiroWebRealm());
//        securityManager.setSessionManager(sessionManager());
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    @DependsOn(value="lifecycleBeanPostProcessor")
    public ShiroWebRealm shiroWebRealm(){
        return new ShiroWebRealm(hashedCredentialsMatcher());
    }

    /**
     * 配置加密匹配，使用MD5的方式，进行1024次加密
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }

//    @Bean
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }

    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redisHost);
        redisManager.setPort(redisPort);
        redisManager.setTimeout(redisTimeout);
        return redisManager;
    }

//    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        // 自定义session管理 使用redis
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    @Bean
    public SimpleCookie rememberMeCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCipherKey(Base64.encode("web".getBytes()));
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    @Bean
    @DependsOn(value="lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

}
