package com.nanyin.config;

import com.nanyin.config.resover.MyLocalResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Bean
    public LocaleResolver localeResolver(){
        return new MyLocalResolver();
    }
}
