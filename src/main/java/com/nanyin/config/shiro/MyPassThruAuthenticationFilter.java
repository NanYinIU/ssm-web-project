package com.nanyin.config.shiro;

import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤 OPTION 请求
 */
public class  MyPassThruAuthenticationFilter extends PassThruAuthenticationFilter {

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if (req.getMethod().equals(RequestMethod.OPTIONS.name())) {
            return true;
        }
        return super.onPreHandle(request, response, mappedValue);
    }

}
