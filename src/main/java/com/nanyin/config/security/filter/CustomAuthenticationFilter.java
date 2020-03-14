package com.nanyin.config.security.filter;

import com.alibaba.fastjson.JSON;
import com.nanyin.config.enums.ResultCodeEnum;
import com.nanyin.config.exceptions.tokenException.TokenEmptyException;
import com.nanyin.config.exceptions.tokenException.TokenExpiredException;
import com.nanyin.config.exceptions.tokenException.TokenParseException;
import com.nanyin.config.exceptions.tokenException.TokenWrongException;
import com.nanyin.config.util.HttpUtils;
import com.nanyin.config.util.JwtUtil;
import com.nanyin.config.util.Result;
import com.nanyin.config.util.SecurityUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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
        String validateToken = null;
        try{
            validateToken  = JwtUtil.getValidateToken(request);
            if (validateToken!=null){
                // 新token
                HttpUtils.setCookie(HttpUtils.buildCookie("TokenKey",validateToken));
            }
            SecurityUtils.checkAuthentication(request);
        } catch (ExpiredJwtException e) {
            Result result = new Result();
            PrintWriter writer = response.getWriter();
            result.setCode(ResultCodeEnum.TOKEN_EXPIRED);
            writer.append(JSON.toJSONString(result));
            return;
        } catch (MalformedJwtException|UnsupportedJwtException|IllegalArgumentException e) {
            Result result = new Result();
            PrintWriter writer = response.getWriter();
            result.setCode(ResultCodeEnum.ILLEGAL_TOKEN);
            writer.append(JSON.toJSONString(result));
            return;
        } catch (Exception e){
            Result result = new Result();
            PrintWriter writer = response.getWriter();
            result.setCode(ResultCodeEnum.FAIL);
            writer.append(JSON.toJSONString(result));
            return;
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
