package com.nanyin.config.locale;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

@Component
public class MyCookieResolver extends CookieLocaleResolver {

    public static final String DEFAULT_COOKIE_NAME = "MyCookieLocaleResolver.locale";

    public MyCookieResolver() {
        setCookieName(DEFAULT_COOKIE_NAME);
    }

    @Override
    public void setDefaultLocale(Locale defaultLocale) {
        super.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
    }

    @Override
    protected Locale getDefaultLocale() {
        return Locale.SIMPLIFIED_CHINESE;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        return super.resolveLocale(request);
    }

}
