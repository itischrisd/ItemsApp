package com.kdudek.itemsapp.controller.advice.ingress;

import com.kdudek.itemsapp.controller.annotation.IfNoneMatch;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class IfNoneMatchArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(IfNoneMatch.class);
    }

    @Override
    public @Nullable Integer resolveArgument(
            MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory
    ) {
        String eTag = webRequest.getHeader(HttpHeaders.IF_NONE_MATCH);
        if (eTag == null || eTag.isBlank()) {
            return null;
        }
        try {
            String stripped = eTag.replace("\"", "").replace("W/", "");
            return Integer.valueOf(stripped);
        } catch (NumberFormatException _) {
            return null;
        }
    }
}
