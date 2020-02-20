package com.nanyin.config.locale;

import com.nanyin.config.util.MDCUtil;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


public class MyLocaleResolver implements LocaleResolver {

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
//        if(locale == null || "".equals(locale)){
//            return Locale.SIMPLIFIED_CHINESE;
//        }else {
//            CommonUtils.check(locale.split("_").length>1,"check_error","resolveLocale");
//            // 在MDC中存放副本
//            Locale locale1 = new Locale(locale.split("_")[0],locale.split("_")[1]);
//            MDCUtil.setLocale(locale1);
//            return locale1;
//        }
        Locale locale = MDCUtil.getLocale();
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
