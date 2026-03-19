package com.kdudek.itemsapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Class<?> type, Long id) {
        super(type.getSimpleName() + " with ID " + id + " not found");
    }
}
