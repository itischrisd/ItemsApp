package com.kdudek.itemsapp.controller.advice.ingress;

import io.github.perplexhub.rsql.RSQLJPASupport;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class RsqlSpecificationArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Specification.class);
    }

    @Override
    public @Nullable Specification<Object> resolveArgument(
            MethodParameter parameter,
            @Nullable ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            @Nullable WebDataBinderFactory binderFactory
    ) {
        String search = webRequest.getParameter(RsqlConstants.QUERY_PARAM_NAME);

        if (search == null || search.trim().isEmpty()) {
            return Specification.unrestricted();
        }

        return RSQLJPASupport.toSpecification(search);
    }

    public static final class RsqlConstants {

        public static final String QUERY_PARAM_NAME = "search";

        private RsqlConstants() {
        }
    }
}
