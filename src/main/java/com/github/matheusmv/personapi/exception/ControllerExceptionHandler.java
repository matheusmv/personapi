package com.github.matheusmv.personapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ResourceNotFoundException exception,
                                                        HttpServletRequest request) {
        var status = HttpStatus.NOT_FOUND;
        var error = getStandardError(exception, request, status);
        return ResponseEntity.status(status).body(error);
    }

    private StandardError getStandardError(RuntimeException exception,
                                           HttpServletRequest request,
                                           HttpStatus status) {
        return StandardError.builder()
                .timestamp(Instant.now())
                .status(status.value())
                .error(exception.getClass().getSimpleName())
                .message(exception.getMessage())
                .path(request.getRequestURI())
                .build();
    }
}
