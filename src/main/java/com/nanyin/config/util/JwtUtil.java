package com.nanyin.config.util;

import com.google.common.base.Strings;
import com.nanyin.config.security.CustomAuthenticatioToken;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * JWT 工具类
 */
public class JwtUtil {

    private final static Logger log = LoggerFactory.getLogger(JwtUtil.class);

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

    private static final String REFRESH = "refresh";

    /**
     * 有效期2小时
     */
    private static final long EXPIRE_TIME = 2 * 60 * 60 * 1000;

    /**
     * 刷新时间
     */
    private static final long REFRESH_TIME = 60 * 60 * 1000;

    /**
     * 生成令牌
     *
     * @return 令牌
     */
    public static String generateToken(Authentication authentication) {
        Map<String, Object> claims = new HashMap<>(4);
        Date refreshDate = new Date(System.currentTimeMillis() + REFRESH_TIME);
        claims.put(REFRESH,refreshDate);
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
    public static String getUsernameFromToken(String token) throws Exception{
        String username;
        if (Strings.isNullOrEmpty(token)) {
            return null;
        }
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        username = claims.getSubject();
        if (username == null) {
            return null;
        }
        return username;
    }

    /**
     * 根据请求令牌获取登录认证信息
     *
     * @return 用户名
     */
    public static Authentication getAuthenticationeFromToken(HttpServletRequest request) throws Exception {
        Authentication authentication = null;
        // 获取请求携带的令牌
        String token = JwtUtil.getToken(request);

        if (Strings.isNullOrEmpty(token)) {
            return null;
        }
        // 请求令牌不能为空
        if (SecurityUtils.getAuthentication() == null) {
            // 上下文中Authentication为空
            Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            String username = claims.getSubject();
            if (username == null) {
                return null;
            }
            if (isTokenExpired(token)) {
                return null;
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
    private static Claims getClaimsFromToken(String token) throws Exception {
        Claims claims = null;
        claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims;
    }

    /**
     * 验证令牌
     *
     * @param token
     * @param username
     * @return
     */
    public static Boolean validateToken(String token, String username) throws Exception {
        String userName = getUsernameFromToken(token);
        return (userName.equals(username) && !isTokenExpired(token));
    }

    public static String getValidateToken(HttpServletRequest request) throws Exception {
        String token = JwtUtil.getToken(request);
        if(token == null){
            return null;
        }
        isTokenExpired(token);
        // 先获取是否token已经过期
        Boolean tokenRefreshExpired = isTokenRefreshExpired(token);
        if(tokenRefreshExpired){
            // 过了刷新时间 重新给token
            return refreshToken(token);
        }else{
            return null;
        }
    }

    /**
     * 刷新令牌
     *
     * @param token
     * @return
     */
    public static String refreshToken(String token) throws Exception {
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
    public static Boolean isTokenExpired(String token) throws Exception {
        Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    public static Boolean isTokenRefreshExpired(String token) throws Exception {
        Claims claims = getClaimsFromToken(token);
        Long expiration = (Long) claims.get(REFRESH);
        return new Date(expiration).before(new Date());
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
