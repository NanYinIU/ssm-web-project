package com.nanyin.config.interceptor;

import com.google.common.base.Strings;
import com.nanyin.config.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

public class WebTokenInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(WebTokenInterceptor.class);

    /*
    使用autowired 需要将  WebTokenInterceptor 注册到容器中，也就是在 WebConfiguration 类中声明为 @Bean
     */
    @Autowired
    RedisService redisService;
    /**
     * 请求前执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 检查token 在缓存中是否存在
        Enumeration<String> headers = request.getHeaders("X-Token");
        String token = "";
        if (headers.hasMoreElements()) {
            token = headers.nextElement();
        }
        if (!Strings.isNullOrEmpty(token)) {
            logger.info("--- get request x-toke:[" + token + "] ---");
            if (redisService.exists(token)) {
                String username = (String) redisService.get(token);
                // 更新缓存中token的时间
                redisService.set(token, username, 3600L);
                return true;
            }
        }
        return false;
    }

    /**
     * 请求后执行
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
