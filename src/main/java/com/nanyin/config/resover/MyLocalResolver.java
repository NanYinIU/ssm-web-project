package com.nanyin.config.resover;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


public class MyLocalResolver implements LocaleResolver {

    private Locale defaultLocale;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        Locale defaultLocale = getDefaultLocale();
        String language = request.getParameter("language");
        String[] locale = new String[2];
        if(language != null && !"".equals(language)){
            locale = getLocale(language);
        }else{
            //如果是空则找浏览器中的属性
            String header = getFirstLangInRequest(request);
            if(header!=null && !"".equals(header)){
                locale = getLocale(header);
            }else{
                //否则给个默认的local
                locale = getLocale("zh-CN");
            }
        }
        defaultLocale = new Locale(locale[0],locale[1]);
        return defaultLocale;
    }

    private String getFirstLangInRequest(HttpServletRequest request) {
        return request.getHeader("Accept-Language").split(",")[0];
    }

    private String[] getLocale(String language) {
        return language.split("-");
    }

    public Locale getDefaultLocale() {
        return this.defaultLocale;
    }
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
