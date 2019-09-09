package com.nanyin.config.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by NanYin on 2019/9/9.
 * swagger2 配置类，参考springfox例子程序
 */
@EnableSwagger2
@Configuration
public class Swagger2Config {

    @Bean
    public Docket userDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("user")
                .apiInfo(userApiInfo())
                .select()
                .paths(or(regex("/user.*"),regex("/user")))
                .build();
    }

    @Bean
    public Docket defaultDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("login")
                .apiInfo(defaultApiInfo())
                .select()
                .paths(or(regex("/signin"),(regex("/index"))
                        ,(regex("/login")),(regex("/logout"))
                        ,(regex("/lang"))))
                .build();
    }

    private ApiInfo userApiInfo(){
        return new ApiInfoBuilder()
                .title("About User API")
                .description(" 本页包含用户相关api.")
                .termsOfServiceUrl("https://github.com/nanyiniu")
                .contact("NanYinIU")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("2.0")
                .build();
    }

    private ApiInfo defaultApiInfo(){
        return new ApiInfoBuilder()
                .title("默认相关 API")
                .description(" 本页包含登录，登出，国际化相关api.")
                .termsOfServiceUrl("https://github.com/nanyiniu")
                .contact("NanYinIU")
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
                .version("2.0")
                .build();
    }
}
