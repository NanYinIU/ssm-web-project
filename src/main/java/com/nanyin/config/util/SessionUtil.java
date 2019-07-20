package com.nanyin.config.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

public class SessionUtil {

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
}
