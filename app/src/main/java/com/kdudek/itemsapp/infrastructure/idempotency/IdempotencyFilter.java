package com.kdudek.itemsapp.infrastructure.idempotency;

import com.kdudek.itemsapp.infrastructure.idempotency.exception.IdempotencyHashMismatchException;
import com.kdudek.itemsapp.infrastructure.idempotency.exception.MalformedIdempotencyKeyException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class IdempotencyFilter extends OncePerRequestFilter {

    private final IdempotencyRepository idempotencyRepository;
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver exceptionHandler;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String keyAsString = request.getHeader("Idempotency-Key");

        if (keyAsString == null || !"POST".equals(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request, 0);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
//        wrappedRequest.getReader().readAllLines();

        UUID idempotencyKey;
        try {
            idempotencyKey = UUID.fromString(keyAsString);
        } catch (IllegalArgumentException _) {
            exceptionHandler.resolveException(request, response, null, new MalformedIdempotencyKeyException());
            return;
        }

        Optional<IdempotencyRecord> maybeRecord = idempotencyRepository.findByIdempotencyKey(idempotencyKey);

        if (maybeRecord.isPresent()) {
            IdempotencyRecord idempotencyRecord = maybeRecord.get();
            wrappedRequest.getReader().readAllLines();
            if (!DigestUtils.md5DigestAsHex(wrappedRequest.getContentAsByteArray()).equals(idempotencyRecord.getRequestHash())) {
                exceptionHandler.resolveException(request, response, null, new IdempotencyHashMismatchException());
                return;
            }
            response.setStatus(idempotencyRecord.getResponseStatus());
            idempotencyRecord.getResponseHeaders().forEach(response::setHeader);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(idempotencyRecord.getJsonResponse());
            return;
        }

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
            int status = wrappedResponse.getStatus();
            Map<String, String> headers = wrappedResponse.getHeaderNames().stream()
                    .filter(name -> wrappedResponse.getHeader(name) != null)
                    .collect(Collectors.toMap(name -> name, wrappedResponse::getHeader));
            byte[] responseBody = wrappedResponse.getContentAsByteArray();
            String jsonResponse = new String(responseBody, StandardCharsets.UTF_8);
            IdempotencyRecord idempotencyRecord = IdempotencyRecord.builder()
                    .idempotencyKey(idempotencyKey)
                    .requestHash(DigestUtils.md5DigestAsHex(wrappedRequest.getContentAsByteArray()))
                    .responseStatus(status)
                    .responseHeaders(headers)
                    .jsonResponse(jsonResponse)
                    .build();
            idempotencyRepository.save(idempotencyRecord);
        } finally {
            wrappedResponse.copyBodyToResponse();
        }
    }
}
