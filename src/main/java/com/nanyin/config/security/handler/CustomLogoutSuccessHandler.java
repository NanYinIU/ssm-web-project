package com.nanyin.config.security.handler;

import com.alibaba.fastjson.JSON;
import com.nanyin.config.enums.ResultCodeEnum;
import com.nanyin.config.util.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 登出后的操作，在配置时需要配置到config中
 */
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 清除 context 信息
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(null);
        SecurityContextHolder.clearContext();
        // 返回内容给前端
        PrintWriter writer = response.getWriter();
        Result result = new Result();
        result.setCode(ResultCodeEnum.TOKEN_EXPIRED);
        writer.append(JSON.toJSONString(result));
    }
}
