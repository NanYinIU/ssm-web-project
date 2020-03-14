package com.nanyin.config.security.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 可自定义LogoutFilter进行登出
 */
public class CustomLogoutFilter extends LogoutFilter {

    LogoutHandler handler;

    public CustomLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler handlers) {
        super(logoutSuccessHandler,handlers);
        this.handler = handlers;
        setFilterProcessesUrl("/logout");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (requiresLogout(request, response)) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            this.handler.logout(request, response, auth);
            return;
        }

        chain.doFilter(request, response);
    }
}
