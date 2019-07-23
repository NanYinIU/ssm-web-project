package com.nanyin.config;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
public class WebErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String,Object> map = super.getErrorAttributes(webRequest,includeStackTrace);
        Map<String,Object> errData = (Map<String, Object>) webRequest.getAttribute("errData", 0);
        map.put("errData",errData);
        return map;
    }

}
