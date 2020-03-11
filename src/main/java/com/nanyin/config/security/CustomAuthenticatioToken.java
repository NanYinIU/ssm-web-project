package com.nanyin.config.security;

import com.nanyin.config.util.HttpUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.Cookie;
import java.util.Collection;

/**
 * 自定义token，继承自UsernamePasswordAuthenticationToken
 */
public class CustomAuthenticatioToken extends UsernamePasswordAuthenticationToken {

    private String token;

    public String getToken() {
        return token;
    }

    public CustomAuthenticatioToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public CustomAuthenticatioToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities,String token) {
        super(principal, credentials, authorities);
        this.token = token;
    }

    public void setToken(String token){
        this.token = token;
//        Cookie tokenCookie = HttpUtils.buildCookie("TokenKey", token);
    }
}
