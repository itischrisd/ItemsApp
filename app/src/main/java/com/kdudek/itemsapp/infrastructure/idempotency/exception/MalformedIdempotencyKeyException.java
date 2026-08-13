package com.kdudek.itemsapp.infrastructure.idempotency.exception;

public class MalformedIdempotencyKeyException extends RuntimeException {

    public MalformedIdempotencyKeyException() {
        super();
    }
}
