package com.nanyin.config.util;

import com.google.common.base.Strings;
import com.nanyin.config.exceptions.tokenException.TokenEmptyException;
import com.nanyin.config.exceptions.tokenException.TokenExpiredException;
import com.nanyin.config.exceptions.tokenException.TokenParseException;
import com.nanyin.config.security.CustomAuthenticatioToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * JWT 解密加密工具类
 */
public class JwtUtil {

    private static final String SECRET_KEY = "54f22da7-1399-4624-bfe6-ce4bc1c6e4c1";

    /**
     * 用户名称
     */
    private static final String USERNAME = Claims.SUBJECT;
    /**
     * 创建时间
     */
    private static final String CREATED = "created";
    /**
     * 权限列表
     */
    private static final String AUTHORITIES = "authorities";

    /**
     * 有效期12小时
     */
    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;


    /**
     * 生成令牌
     *
     * @return 令牌
     */
    public static String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>(3);
        claims.put(USERNAME, SecurityUtils.getUsername(authentication));
        claims.put(CREATED, new Date());
        claims.put(AUTHORITIES, authentication.getAuthorities());
        return generateToken(claims);
    }


    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private static String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        String username;
        if (Strings.isNullOrEmpty(token)) {
            throw new TokenEmptyException();
        }

        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            throw new TokenParseException();
        }
        username = claims.getSubject();
        if (username == null) {
            throw new TokenParseException();
        }
        return username;
    }

    /**
     * 根据请求令牌获取登录认证信息
     *
     * @return 用户名
     */
    public static Authentication getAuthenticationeFromToken(HttpServletRequest request) {
        Authentication authentication = null;
        // 获取请求携带的令牌
        String token = JwtUtil.getToken(request);

        if (Strings.isNullOrEmpty(token)) {
            throw new TokenEmptyException();
        }
        // 请求令牌不能为空
        if (SecurityUtils.getAuthentication() == null) {
            // 上下文中Authentication为空
            Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                throw new TokenParseException();
            }
            String username = claims.getSubject();
            if (username == null) {
                throw new TokenParseException();
            }
            if (isTokenExpired(token)) {
                throw new TokenExpiredException();
            }
            Object authors = claims.get(AUTHORITIES);
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                if (authors != null && authors instanceof List) {
                    for (Object object : (List) authors) {
                        authorities.add(new SimpleGrantedAuthority((String) ((Map) object).get("authority")));
                    }
                }
            authentication = new CustomAuthenticatioToken(username, null, authorities, token);
        } else {
            if (validateToken(token, SecurityUtils.getUsername())) {
                // 如果上下文中Authentication非空，且请求令牌合法，直接返回当前登录认证信息
                authentication = SecurityUtils.getAuthentication();
            }
        }

        return authentication;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static Claims getClaimsFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        if (claims == null) {
            throw new TokenParseException();
        }
        return claims;
    }

    /**
     * 验证令牌
     *
     * @param token
     * @param username
     * @return
     */
    public static Boolean validateToken(String token, String username) {
        String userName = getUsernameFromToken(token);
        return (userName.equals(username) && !isTokenExpired(token));
    }

    /**
     * 刷新令牌
     *
     * @param token
     * @return
     */
    public static String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public static Boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }


    /**
     * 获取请求token
     *
     * @param request
     * @return
     */
    public static String getToken(HttpServletRequest request) {
        String token = request.getHeader("X-Token");
        String tokenHead = "Bearer ";
        if (token == null) {
            token = request.getHeader("token");
        } else if (token.contains(tokenHead)) {
            token = token.substring(tokenHead.length());
        }
        if ("".equals(token)) {
            token = null;
        }
        return token;
    }


}
