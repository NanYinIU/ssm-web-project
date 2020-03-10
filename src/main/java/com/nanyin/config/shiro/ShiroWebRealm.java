package com.nanyin.config.shiro;

import com.nanyin.config.exceptions.TokenExpiredException;
import com.nanyin.config.exceptions.UserExistedException;
import com.nanyin.entity.Permission;
import com.nanyin.entity.Role;
import com.nanyin.entity.User;
import com.nanyin.services.UserServices;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class ShiroWebRealm extends AuthorizingRealm {
    @Autowired
    UserServices userServices;
    /**
     * 授权
     * @Author nanyin
     * @Date 09:09 2019-07-23
     * @param principalCollection 1
     * @return org.apache.shiro.authz.AuthorizationInfo
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = getUser(username);
        if(user == null){
            throw new TokenExpiredException();
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = new HashSet<>();
        Set<String> permissions = new HashSet<>();
        for (Role r:user.getRoles()
             ) {
            roles.add(r.getName());
            for (Permission permission : r.getPermissions()) {
                permissions.add(permission.getName());
            }
        }
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 身份认证
     * @Author nanyin
     * @Date 09:08 2019-07-23
     * @param authenticationToken 1
     * @return org.apache.shiro.authc.AuthenticationInfo
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        if(!"".equals(username) && username!=null){
            User user = getUser(username);
            ByteSource byteSource = ByteSource.Util.bytes(user.getSalt());
            return new SimpleAuthenticationInfo(user,user.getPassword(),byteSource,"");
        }else {
            return null;
        }
    }

    public ShiroWebRealm(CredentialsMatcher matcher) {
        super(matcher);
    }

    private User getUser(String username){
        User user = null;
        try{
            user = userServices.getUserFromUserName(username);
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }

}
