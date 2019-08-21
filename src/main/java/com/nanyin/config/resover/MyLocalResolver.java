package com.nanyin.config.resover;

import com.nanyin.config.util.MDCUtil;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


public class MyLocalResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//        String locale = null;
//        for (Cookie cookie:cookies
//             ) {
//            if("locale".equals(cookie.getName())){
//                locale = cookie.getValue();
//            }
//        }
//        if(locale == null){
//            return Locale.SIMPLIFIED_CHINESE;
//        }else {
//            CommonUtil.check(locale.split("_").length>1,"check_error","resolveLocale");
//            // 在MDC中存放副本
//            Locale locale1 = new Locale(locale.split("_")[0],locale.split("_")[1]);
//            MDCUtil.setLocale(locale1);
//            return locale1;
//        }
        return MDCUtil.getLocale();
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
