package com.nanyin.config.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class HttpsUtil {

    public static Session getSession() {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        return session;
    }

    public static Session setAttribute(Object key, Object value) {
        Session session = getSession();
        session.setAttribute(key, value);
        return session;
    }

    public static Object getAttribute(Object key) {
        Session session = getSession();
        return session.getAttribute(key);
    }


    public static void setCookie(HttpServletResponse response, String cookieName, String value) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setPath("/");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
    }

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes sra = null;
        if ((sra = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())) != null) {
            return sra.getRequest();
        } else {
            return null;
        }
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
