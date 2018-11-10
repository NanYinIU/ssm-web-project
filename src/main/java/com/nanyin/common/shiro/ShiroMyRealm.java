package com.nanyin.common.shiro;

import com.nanyin.entity.User;
import com.nanyin.services.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by NanYin on 2017-07-09 上午9:36.
 * 包名： com.nanyin.common.shiro
 * 类描述：自定义realm
 */
public class ShiroMyRealm extends AuthorizingRealm {
    private static final Logger logger = LoggerFactory.getLogger(ShiroMyRealm.class);
    @Autowired
    private UserService userService;
//    授予权限和角色 已经登录成功的
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("##############执行权限认证###############");
        String username = (String) principalCollection.getPrimaryPrincipal(); //获取用户名
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.getRoles(username));
        authorizationInfo.setStringPermissions(userService.getPermissions(username));
        return authorizationInfo;
    }
//验证当前用户信息
        protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
// 先拿到用户名 去比对 name不可能重复 去数据库里面去查如果用户存在获取认证信息 不存在返回空
        String username = (String) authenticationToken.getPrincipal();

        User user = (User) userService.selectByName(username);
        if(user != null){
//  只有当前用户存在 并且状态未被锁定 才能能登录
            if(user.getStatus()==0) {
                AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, user.getPassword(), "myreaml");
                return authenticationInfo;
            }else return null;
        }else return null ;

    }
}
