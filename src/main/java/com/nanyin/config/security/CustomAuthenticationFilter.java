package com.nanyin.config.security;

import com.alibaba.fastjson.JSON;
import com.nanyin.config.enums.ResultCodeEnum;
import com.nanyin.config.exceptions.tokenException.TokenEmptyException;
import com.nanyin.config.exceptions.tokenException.TokenExpiredException;
import com.nanyin.config.exceptions.tokenException.TokenParseException;
import com.nanyin.config.exceptions.tokenException.TokenWrongException;
import com.nanyin.config.util.Result;
import com.nanyin.config.util.SecurityUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 自定义url的过滤器，主要对token的验证
 */
public class CustomAuthenticationFilter extends BasicAuthenticationFilter {

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.request = request;
        this.response = response;
        this.chain = chain;
        // 获取token, 并检查登录状态
        PrintWriter writer = response.getWriter();
        Result result = new Result();
        try {
            SecurityUtils.checkAuthentication(request);
        } catch (TokenEmptyException | TokenExpiredException | TokenWrongException | TokenParseException e) {
            // 拦截 checkAuthentication 时发生的异常，尽量所有的异常都向外抛出，而不是catch
            result.setCode(ResultCodeEnum.ILLEGAL_TOKEN);
            result.setMessage(e.getMessage());
            writer.append(JSON.toJSONString(result));
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        chain.doFilter(request, response);
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public FilterChain getChain() {
        return chain;
    }

    public void setChain(FilterChain chain) {
        this.chain = chain;
    }
}
