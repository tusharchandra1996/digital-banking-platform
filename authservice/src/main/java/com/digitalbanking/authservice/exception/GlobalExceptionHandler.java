package com.digitalbanking.authservice.exception;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.digitalbanking.authservice.dto.common.ErrorResponse;
import com.digitalbanking.authservice.util.CorrelationIdUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(
            AuthException exception,
            HttpServletRequest request
    ) {
        String correlationId = CorrelationIdUtil.getCorrelationId(request);

        AuthErrorCode errorCode = exception.getErrorCode();

        ErrorResponse response = new ErrorResponse(
                correlationId,
                LocalDateTime.now(),
                errorCode.getCode(),
                exception.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        String correlationId = CorrelationIdUtil.getCorrelationId(request);

        Map<String, String> validationErrors = new HashMap<>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> validationErrors.put(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()
                ));

        ErrorResponse response = new ErrorResponse(
                correlationId,
                LocalDateTime.now(),
                AuthErrorCode.VALIDATION_FAILED.getCode(),
                AuthErrorCode.VALIDATION_FAILED.getMessage(),
                request.getRequestURI(),
                validationErrors
        );

        return ResponseEntity
                .status(AuthErrorCode.VALIDATION_FAILED.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException exception,
            HttpServletRequest request
    ) {
        String correlationId = CorrelationIdUtil.getCorrelationId(request);

        ErrorResponse response = new ErrorResponse(
                correlationId,
                LocalDateTime.now(),
                AuthErrorCode.USER_REGISTRATION_FAILED.getCode(),
                AuthErrorCode.USER_REGISTRATION_FAILED.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity
                .status(AuthErrorCode.USER_REGISTRATION_FAILED.getHttpStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception exception,
            HttpServletRequest request
    ) {
        String correlationId = CorrelationIdUtil.getCorrelationId(request);
        System.err.println("ERROR OCCURRED: " + exception.getMessage());
        ErrorResponse response = new ErrorResponse(
                correlationId,
                LocalDateTime.now(),
                AuthErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                AuthErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity
                .status(AuthErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(response);
    }
}
