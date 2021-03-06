package com.nanyin.config;

import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.nanyin.config.interceptor.WebTokenInterceptor;
import com.nanyin.config.locale.MyCookieResolver;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.persistence.EntityManager;
import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public CookieLocaleResolver localeResolver(){
        return new MyCookieResolver();
    }

    @Bean
    public WebTokenInterceptor tokenInterceptor(){
        return new WebTokenInterceptor();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许跨域访问的路径
        registry.addMapping("/**")
                // 允许跨域访问的源
                .allowedOrigins("*")
                // 允许请求方法
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                // 预检间隔时间
                .maxAge(168000)
                // 允许头部设置
                .allowedHeaders("*")
                // 是否发送cookie
                .allowCredentials(true);
    }

    /**
     * 使用 fastjson 配置消息转换器
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //自定义配置...
        FastJsonConfig config = new FastJsonConfig();
        config.setCharset(Charset.forName("utf-8"));
        converter.setFastJsonConfig(config);
        converters.add(0, converter);
    }

    /**
     * 添加interceptors
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(logbackInterceptor());
        // 需要实现对token的解析
//        registry.addInterceptor(tokenInterceptor()).excludePathPatterns("/user/login");
    }


    /**
     * 注入 jpaQueryFactory
     * @param entityManager
     * @return
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager){
        return new JPAQueryFactory(entityManager);
    }

}
