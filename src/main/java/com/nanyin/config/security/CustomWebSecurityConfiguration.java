package com.nanyin.config.security;

import com.nanyin.services.impl.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * 使用SpringSecurity替代Shiro完成登陆、鉴权功能
 * 1. 添加 spring-boot-starter-security
 * 2. 使用EnableWebSecurity注解开启Spring Security
 * 3. 编写配置类继承WebSecurityConfigurerAdapter
 * 4. 添加自定义过滤器
 *
 *
 * 需要注意的是：
 * - http.cors() 设置跨域
 * - .csrf().disable() 禁用csrf，正常使用put。。。等请求
 *
 * 关于角色/权限应用如下规范
 *
 * 1. 角色后台随意定义，角色包含权限，权限数据库中设定，不能更改，程序中使用hasAuthority获得具体权限进行方法级权限限制
 * 2. 权限固定三种 write（增加，删除元素）、read（查看元素）、execute（比如执行特定页面操作时可用）
 * 3. 在UserDetailsServiceImpl生成的GrantedAuthority 具体的 角色_权限，并且 字母全部大写
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomWebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    private UserServicesImpl userDetailsService;

    public CustomWebSecurityConfiguration(UserServicesImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder, PasswordEncoder passwordEncoder) throws Exception {
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 跨域预检请求
                .antMatchers("/user/login").permitAll() // 除了 登陆页面不需要验证权限
                // swagger
                .antMatchers("/swagger**/**").permitAll()
                .anyRequest().authenticated(); //其他都需要权限验证
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
        // 禁用缓存
        http.headers().cacheControl();
        // 自定义过滤器
        http.addFilterBefore(new CustomAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        // 编码
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
