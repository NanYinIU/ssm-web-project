package com.nanyin.config.interceptor;

import com.nanyin.config.util.CommonUtil;
import com.nanyin.config.util.MDCUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogbackInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogbackInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        MDCUtil.setUser((String) httpServletRequest.getSession().getAttribute("username"));
        String uuid = CommonUtil.generateRequestUid();
        // 从session中获取 username 放到mdc中
        MDCUtil.setRequestId(uuid);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // 清空mdc内容
        MDCUtil.clearAllUserInfo();
    }
}
