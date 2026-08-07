package com.kdudek.itemsapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
public class PreconditionRequiredException extends RuntimeException {

    public PreconditionRequiredException() {
        super();
    }
}
