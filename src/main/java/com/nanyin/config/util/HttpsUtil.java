package com.nanyin.config.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class HttpsUtil {

    public static Session getSession(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        return session;
    }

    public static Session setAttribute(Object key,Object value){
        Session session = getSession();
        session.setAttribute(key, value);
        return session;
    }

    public static Object getAttribute(Object key){
        Session session = getSession();
        return session.getAttribute(key);
    }

    public static String getCookie(HttpServletRequest request, String cookieName){
        Cookie[] cookies =  request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(cookieName)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void setCookie(HttpServletResponse response, String cookieName, String value){
        Cookie cookie = new Cookie(cookieName,value);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }

}
