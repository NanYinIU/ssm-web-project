package com.nanyin.config.shiro;

import com.nanyin.config.exceptions.NoUserAccountException;
import com.nanyin.config.exceptions.UserIsBlockException;
import com.nanyin.entity.Auth;
import com.nanyin.entity.Role;
import com.nanyin.entity.User;
import com.nanyin.enumEntity.StatusEnum;
import com.nanyin.services.UserServices;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

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
        User user = userServices.getUserFromUserName(username);
        if(user == null){
            throw new NoUserAccountException();
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<String> roles = new ArrayList<>();
        for (Role r:user.getRoles()
             ) {
            roles.add(r.getName());
        }
        List<String> auths = new ArrayList<>();
        for(Auth a:user.getAuths()){
            auths.add(a.getName());
        }
        simpleAuthorizationInfo.addRoles(roles);
        simpleAuthorizationInfo.addStringPermissions(auths);
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
        String username = (String) authenticationToken.getPrincipal();
        User user = null;
        try{
            user = userServices.getUserFromUserName(username);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(user == null ){
            throw new NoUserAccountException();
        }
        //非正常状态
        if(!user.getStatus().getId().equals(StatusEnum.NORMAL.getId())){
            throw new UserIsBlockException();
        }
        ByteSource byteSource = ByteSource.Util.bytes(user.getSalt());
        return new SimpleAuthenticationInfo(username,user.getPassword(),byteSource,"");
    }

    public ShiroWebRealm(CredentialsMatcher matcher) {
        super(matcher);
    }

}
