package com.nanyin.config.shiro;

import com.nanyin.config.exceptions.NoUserAccountException;
import com.nanyin.config.exceptions.UserIsBlockException;
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

public class ShiroWebRealm extends AuthorizingRealm {
    @Autowired
    UserServices userServices;
    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        User user = userServices.getUserFromUserName(username);
        if(user == null){
            throw new NoUserAccountException();
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(user.getRoles());
        simpleAuthorizationInfo.addStringPermissions(user.getAuths());
        return simpleAuthorizationInfo;
    }

    // 身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        User user = userServices.getUserFromUserName(username);
        if(user == null ){
            throw new NoUserAccountException();
        }
        //非正常状态
        if(!user.getStatus().equals(StatusEnum.NORMAL.getId())){
            throw new UserIsBlockException();
        }
        ByteSource byteSource = ByteSource.Util.bytes(user.getSalt());
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,user.getPassword(),byteSource,"");
        return simpleAuthenticationInfo;
    }

    public ShiroWebRealm(CredentialsMatcher matcher) {
        super(matcher);
    }

}
