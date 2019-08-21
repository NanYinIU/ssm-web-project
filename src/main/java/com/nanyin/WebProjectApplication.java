package com.nanyin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.nanyin.config.util.MDCUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class WebProjectApplication {
    public static void main(String[] args) {
        MDCUtil.setUser("ADMIN");
        JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect.getMask();
        SpringApplication.run(WebProjectApplication.class);
    }
}
