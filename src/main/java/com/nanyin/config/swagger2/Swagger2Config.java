//package com.nanyin.config.swagger2;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import static com.google.common.base.Predicates.or;
//import static springfox.documentation.builders.PathSelectors.regex;
//
///**
// * Created by NanYin on 2019/9/9.
// * swagger2 配置类，参考springfox例子程序
// */
//@EnableSwagger2
//@Configuration
//public class Swagger2Config {
//
//    @Bean
//    public Docket userDocket(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("user")
//                .apiInfo(userApiInfo())
//                .select()
//                .paths(or(regex("/user")))
//                .build();
//    }
//
//
//    private ApiInfo userApiInfo(){
//        return new ApiInfoBuilder()
//                .title("Springfox petstore API")
//                .description("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum " +
//                        "has been the industry's standard dummy text ever since the 1500s, when an unknown printer "
//                        + "took a " +
//                        "galley of type and scrambled it to make a type specimen book. It has survived not only five " +
//                        "centuries, but also the leap into electronic typesetting, remaining essentially unchanged. " +
//                        "It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum " +
//                        "passages, and more recently with desktop publishing software like Aldus PageMaker including " +
//                        "versions of Lorem Ipsum.")
//                .termsOfServiceUrl("http://springfox.io")
//                .contact("springfox")
//                .license("Apache License Version 2.0")
//                .licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
//                .version("2.0")
//                .build();
//    }
//}
