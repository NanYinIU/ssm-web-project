package com.nanyin.config.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpUtils {

    public static ServletRequestAttributes getServletRequestAttributes(){
        //获取到ServletRequestAttributes 里面有
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpServletRequest getHttpServletRequest() throws Exception{
        //获取到Request对象
        return getServletRequestAttributes().getRequest();
    }

    public static HttpServletResponse getHttpServletResponse(){
       return getServletRequestAttributes().getResponse();
    }

    public static HttpSession getSession() throws Exception{
        //HttpServletResponse response = attrs.getResponse();
        return getHttpServletRequest().getSession();
    }

    public static HttpSession setAttribute(String key, Object value) throws Exception{
        HttpSession session = getSession();
        session.setAttribute(key, value);
        return session;
    }

    public static Object getAttribute(String key) throws Exception {
        HttpSession session = getSession();
        return session.getAttribute(key);
    }

    public static Cookie buildCookie(String cookieName, String value){
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(3600);
        cookie.setPath("/");
        return cookie;
    }

    public static void setCookie(Cookie ... cookie) {
        HttpServletResponse response = getHttpServletResponse();
        for (Cookie c : cookie) {
            response.addCookie(c);
        }
    }

    public static Cookie getCookie(String cookieName) throws Exception{
        Cookie[] cookies = getHttpServletRequest().getCookies();
        for (Cookie cookie : cookies) {
            if(cookieName.equals(cookie.getName())){
                return cookie;
            }
        }
        return null;
    }

    public static String getRequestIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }

    public static String getRequestOs(HttpServletRequest request) {
        String browserDetails = request.getHeader("User-Agent");
        String userAgent = browserDetails;
        String os;
        if (userAgent.toLowerCase().contains("windows")) {
            os = "Windows";
        } else if (userAgent.toLowerCase().contains("mac")) {
            os = "Mac";
        } else if (userAgent.toLowerCase().contains("x11")) {
            os = "Unix";
        } else if (userAgent.toLowerCase().contains("android")) {
            os = "Android";
        } else if (userAgent.toLowerCase().contains("iphone")) {
            os = "IPhone";
        } else {
            os = "UnKnown, More-Info: " + userAgent;
        }
        return os;
    }
}
