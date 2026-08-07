package com.kdudek.itemsapp.controller.advice.ingress;

import com.kdudek.itemsapp.controller.annotation.IfMatch;
import com.kdudek.itemsapp.exception.PreconditionFailedException;
import com.kdudek.itemsapp.exception.PreconditionRequiredException;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class IfMatchArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(IfMatch.class);
    }

    @Override
    public @Nullable Integer resolveArgument(
            MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory
    ) {
        String eTag = webRequest.getHeader(HttpHeaders.IF_MATCH);
        if (eTag == null) {
            throw new PreconditionRequiredException();
        }
        try {
            String stripped = eTag.replace("\"", "").replace("W/", "");
            return Integer.valueOf(stripped);
        } catch (NumberFormatException _) {
            throw new PreconditionFailedException();
        }
    }
}
