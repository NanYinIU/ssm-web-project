package com.nanyin.config.security;

import com.nanyin.config.security.filter.CustomAuthenticationFilter;
import com.nanyin.config.security.handler.CustomAccessDeniedHandler;
import com.nanyin.config.security.handler.CustomAuthenticationEntryPoint;
import com.nanyin.config.security.handler.CustomLogoutSuccessHandler;
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
 * 登出、续签等问题
 *
 * 1. 主要需要完成对SpringSecurity中的状态清除
 * 2. 需要处理未过期的token
 *      - 首先
 *             采用黑名单策略
 *          * 每次访问logout接口时，对未过期的token添加到redis中，设置redis对于该token的过期时间为token的剩余时间
 *          * 使用token验证时，先在redis中查找是否该token在黑名单中，如果在，则限制登陆
 *             采用白名单策略
 *          * 如果使用白名单，每次添加token时，只有有效的token添加只redis中
 *          * 每次更新token时，都会一起更新redis中的token
 *      - 其次 在使用密码修改操作时
 *          * 原来的token自然不能使用,使用上面的logout进行作废
 *          * 新签发一个token，放到header中
 *      - 最后 在续签问题上
 *          * 设置一个过期时间 expire 和刷新时间 refresh ，refresh < expire
 *          * 在刷新时间内，不更换token，如果超过刷新时间，更换新的token
 *          * 超过过期时间，则退出登陆，重新登陆
 *          * 修改密码和自己推出登陆时
 *
 *
 *  3. 最后采用方案：
 *
 *      暂时不使用redis记录白名单或者黑名单，这时会带来的问题就是以前未过期的token还能接着用。使用续签，可无限续签
 *
 *      todo: 1. 增加黑名单或白名单，防止未过期token仍然有效问题
 *
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

    @Bean
    public CustomLogoutSuccessHandler logoutSuccessHandler(){
        return new CustomLogoutSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(jwtAccessDeniedHandler())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                // 跨域预检请求
                .antMatchers("/login").permitAll() // 除了 登陆页面不需要验证权限
                // swagger
                .antMatchers("/swagger**/**").permitAll()
                .anyRequest().authenticated(); //其他都需要权限验证

        http.logout().logoutUrl("/logout").logoutSuccessHandler(new CustomLogoutSuccessHandler());
        // 禁用缓存
        http.headers().cacheControl();
        // 编码
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter, CsrfFilter.class);

        // 自定义过滤器
        http.addFilterBefore(new CustomAuthenticationFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public CustomAccessDeniedHandler jwtAccessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomAuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }
    /**
     * 配置加密策略
     * @return
     */
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
