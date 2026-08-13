package com.kdudek.itemsapp.infrastructure.idempotency.exception;

public class IdempotencyHashMismatchException extends RuntimeException {

    public IdempotencyHashMismatchException() {
        super();
    }
}
