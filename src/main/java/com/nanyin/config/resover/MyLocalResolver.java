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
        String[] s = new String[2];
        if(language != null && !"".equals(language)){
            s = getS(language);
        }else{
            //如果是空则找浏览器中的属性
            String header = getFirstLangInRequest(request);
            if(header!=null && !"".equals(header)){
                s = getS(header);
            }else{
                //否则给个默认的local
                s = getS("zh-CN");
            }
        }
        defaultLocale = new Locale(s[0],s[1]);
        return defaultLocale;
    }

    private String getFirstLangInRequest(HttpServletRequest request) {
        return request.getHeader("Accept-Language").split(",")[0];
    }

    private String[] getS(String language) {
        return language.split("-");
    }

    public Locale getDefaultLocale() {
        return this.defaultLocale;
    }
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
