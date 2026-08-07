package com.kdudek.itemsapp.controller.advice.egress;

import com.kdudek.itemsapp.dto.response.common.IdentifiableResource;
import com.kdudek.itemsapp.dto.response.common.PageResponse;
import com.kdudek.itemsapp.dto.response.common.VersionedResource;
import com.kdudek.itemsapp.exception.ResourceNotModifiedException;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.Instant;
import java.util.List;

@ControllerAdvice
public class PagedAndVersionedResponseAdvice implements ResponseBodyAdvice<PageResponse<IdentifiableResource>> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return PageResponse.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public @Nullable PageResponse<@NonNull IdentifiableResource> beforeBodyWrite(
            @Nullable PageResponse<IdentifiableResource> body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        if (body == null) {
            return null;
        }

        List<IdentifiableResource> items = body.getContent();

        long hash = 17;
        Instant lastModified = Instant.MIN;

        for (IdentifiableResource item : items) {
            hash = 31 * hash + item.getId().hashCode();
            if (item instanceof VersionedResource itemWithVersion) {
                hash = 31 * hash + itemWithVersion.getVersion().hashCode();
                Instant lastResourceModification = (itemWithVersion.getUpdatedAt() != null)
                        ? itemWithVersion.getUpdatedAt()
                        : itemWithVersion.getCreatedAt();
                lastModified = lastModified.isBefore(lastResourceModification)
                        ? lastResourceModification
                        : lastModified;
            }
        }
        hash = 31 * hash + Long.hashCode(items.size());
        String eTag = "W/\"" + hash + "\"";

        response.getHeaders().setETag(eTag);
        response.getHeaders().setLastModified(lastModified);

        List<String> ifNoneMatch = request.getHeaders().getIfNoneMatch();

        if (ifNoneMatch.isEmpty()) {
            return body;
        } else if (eTag.equals(ifNoneMatch.getFirst())) {
            throw new ResourceNotModifiedException();
        } else {
            return body;
        }
    }
}
