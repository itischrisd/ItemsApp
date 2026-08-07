package com.kdudek.itemsapp.controller.advice.egress;

import com.kdudek.itemsapp.dto.response.common.IdentifiableResource;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@ControllerAdvice
public class LocationHeaderResponseAdvice implements ResponseBodyAdvice<IdentifiableResource> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.hasMethodAnnotation(PostMapping.class) && IdentifiableResource.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public @Nullable IdentifiableResource beforeBodyWrite(
            @Nullable IdentifiableResource body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        if (body == null) {
            return null;
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(body.getId())
                .toUri();
        response.getHeaders().setLocation(location);
        return body;
    }
}
