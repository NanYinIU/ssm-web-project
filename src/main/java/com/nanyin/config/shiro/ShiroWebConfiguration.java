package com.nanyin.config.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroWebConfiguration {

    private Logger logger = LoggerFactory.getLogger(ShiroWebConfiguration.class);
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setLoginUrl("/signin");
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap());
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        return shiroFilterFactoryBean;
    }

    public Map<String, String> filterChainDefinitionMap(){
        Map<String,String> map = new HashMap<>();
        map.put("/assets/**","anon");
        map.put("/webjars/**","anon");
        map.put("/signin?language=zh-CN","anon");
        map.put("/login","anon");
        map.put("/**","authc");
        return map;
    }
    /**
     * SecurityManager 安全管理器；Shiro的核心
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        logger.info("开启安全管理器-----------------------");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        logger.info("加载realm-----------------------");
        securityManager.setRealm(shiroWebRealm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public ShiroWebRealm shiroWebRealm(){
        return new ShiroWebRealm(hashedCredentialsMatcher());
    }

    /**
     * 配置加密匹配，使用MD5的方式，进行1024次加密
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(){
        DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
        defaultWebSessionManager.setGlobalSessionTimeout(1800000);
        defaultWebSessionManager.setDeleteInvalidSessions(true);
        defaultWebSessionManager.setSessionDAO(new MemorySessionDAO());
        defaultWebSessionManager.setSessionIdCookieEnabled(true);
        defaultWebSessionManager.setSessionIdCookie(new SimpleCookie("simpleCookie"));
        return defaultWebSessionManager;
    }
}
