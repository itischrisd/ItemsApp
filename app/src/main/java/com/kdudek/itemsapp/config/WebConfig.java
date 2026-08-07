package com.kdudek.itemsapp.config;

import com.kdudek.itemsapp.controller.advice.ingress.IfMatchArgumentResolver;
import com.kdudek.itemsapp.controller.advice.ingress.IfNoneMatchArgumentResolver;
import com.kdudek.itemsapp.controller.advice.ingress.RsqlSpecificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new RsqlSpecificationArgumentResolver());
        resolvers.add(new IfMatchArgumentResolver());
        resolvers.add(new IfNoneMatchArgumentResolver());
    }
}
