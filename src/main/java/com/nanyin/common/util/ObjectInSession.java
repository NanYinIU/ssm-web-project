package com.nanyin.common.util;

import com.nanyin.entity.user.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @Auther: NanYin
 * @Date: 12/2/18 17:59
 * @Description:
 */
public class ObjectInSession {

    private static Subject SUBJECT = SecurityUtils.getSubject();
    private static Session SESSION = SUBJECT.getSession();

    public static User getUserInSession(){
         return (User) SESSION.getAttribute("user");
    }
}