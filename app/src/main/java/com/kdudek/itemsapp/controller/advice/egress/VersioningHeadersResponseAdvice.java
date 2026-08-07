package com.kdudek.itemsapp.controller.advice.egress;

import com.kdudek.itemsapp.dto.response.common.VersionedResource;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.Instant;

@ControllerAdvice
public class VersioningHeadersResponseAdvice implements ResponseBodyAdvice<VersionedResource> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return VersionedResource.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public @Nullable VersionedResource beforeBodyWrite(
            @Nullable VersionedResource body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        if (body == null) {
            return null;
        }
        response.getHeaders().setETag("\"" + body.getVersion() + "\"");
        Instant lastModification = body.getUpdatedAt() != null ? body.getUpdatedAt() : body.getCreatedAt();
        response.getHeaders().setLastModified(lastModification);
        return body;
    }
}
