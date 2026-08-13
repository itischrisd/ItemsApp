package com.kdudek.itemsapp.infrastructure.idempotency;

import com.kdudek.itemsapp.controller.advice.egress.RestExceptionHandler.Constants;
import com.kdudek.itemsapp.infrastructure.idempotency.exception.IdempotencyHashMismatchException;
import com.kdudek.itemsapp.infrastructure.idempotency.exception.MalformedIdempotencyKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class IdempotencyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MalformedIdempotencyKeyException.class)
    public ProblemDetail handleMalformedIdempotencyKey() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Provided Idempotency-Key is not a valid UUID."
        );
        problemDetail.setTitle("Bad Request");
        problemDetail.setProperty(Constants.TIMESTAMP_FIELD_NAME, Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(IdempotencyHashMismatchException.class)
    public ProblemDetail handleIdempotencyHashMismatch() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNPROCESSABLE_CONTENT,
                "Payload differs for this Idempotency-Key."
        );
        problemDetail.setTitle("Unprocessable Content");
        problemDetail.setProperty(Constants.TIMESTAMP_FIELD_NAME, Instant.now());
        return problemDetail;
    }
}
