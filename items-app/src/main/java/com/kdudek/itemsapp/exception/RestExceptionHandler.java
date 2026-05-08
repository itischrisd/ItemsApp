package com.kdudek.itemsapp.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            ResourceNotFoundException.class,
            RelatedResourceNotFoundException.class
    })
    public ProblemDetail handleResourceNotFound(Throwable e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                e.getLocalizedMessage()
        );
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.getMessage(), e);
        return handleResourceNotFound(e);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            @NonNull MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request
    ) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Validation failed for one or more fields."
        );
        problemDetail.setTitle("Invalid Request Parameters");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("errors", ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                )));
        return ResponseEntity.badRequest().headers(headers).body(problemDetail);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolation(ConstraintViolationException e) {
        log.error("Late handling of constraint violation", e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Validation failed for one or more fields."
        );
        problemDetail.setTitle("Invalid Request Parameters");
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("errors", e.getConstraintViolations()
                .stream()
                .collect(Collectors.groupingBy(
                        constraintViolation -> {
                            String fieldName = "";
                            for (Path.Node node : constraintViolation.getPropertyPath()) {
                                fieldName = node.getName();
                            }
                            return fieldName;
                        },
                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList())
                )));
        return problemDetail;
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ProblemDetail handleTransactionSystemException(TransactionSystemException e) {
        if (e.getRootCause() instanceof ConstraintViolationException cve) {
            return handleConstraintViolation(cve);
        }
        log.error("TransactionSystemException that was not ConstraintViolationException occured", e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "Operation couldn't be completed due to data conflict."
        );
        problemDetail.setTitle("Datbase Conflict");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "Operation couldn't be completed due to data conflict."
        );
        problemDetail.setTitle("Datbase Conflict");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ProblemDetail handleOptimisticLockingFailure(OptimisticLockingFailureException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                "Operation requested for stale data."
        );
        problemDetail.setTitle("Conflict");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    public ProblemDetail handleAccessDenied() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                "Insufficient rights to perform action or access resource"
        );
        problemDetail.setTitle("Access Denied");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    public ProblemDetail handleAuthentication() {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                "Not authenticated or session expired"
        );
        problemDetail.setTitle("Not Authenticated");
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}
