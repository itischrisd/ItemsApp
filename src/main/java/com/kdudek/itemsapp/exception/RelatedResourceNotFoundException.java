package com.kdudek.itemsapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RelatedResourceNotFoundException extends RuntimeException {

    public RelatedResourceNotFoundException(Class<?> parentType, Long parentId, Class<?> childType, Long childId) {
        super(parentType.getSimpleName() + " with ID " + parentId + " does not have " + childType.getSimpleName() + " with ID " + childId);
    }
}
