package com.nanyin.common.shiro;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by NanYin on 2017-07-11 下午10:10.
 * 包名： com.nanyin.common.shiro
 * 类描述：ajax 超时处理
 */
@Component
public class AjaxUserFilter extends UserFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response) throws Exception {

        HttpServletRequest req = WebUtils.toHttp(request);
        String xmlHttpRequest = req.getHeader("X-Requested-With");
        if ( xmlHttpRequest != null )
            if ( xmlHttpRequest.equalsIgnoreCase("XMLHttpRequest") )  {
                HttpServletResponse res = WebUtils.toHttp(response);
                res.sendError(401);
                return false;
            }

        return super.onAccessDenied(request, response);
    }
}