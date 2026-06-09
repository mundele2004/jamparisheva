package com.example.paymentgateway.khatian.exception;

import com.example.paymentgateway.khatian.dto.ApiResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.example.paymentgateway.khatian")
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class KhatianExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining(", "));
        log.error("Khatian request validation failed: {}", message);
        return ResponseEntity.badRequest().body(ApiResponseDTO.failure(message));
    }

    @ExceptionHandler(KhatianNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleNotFound(KhatianNotFoundException ex) {
        log.error("Khatian record lookup failed: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponseDTO.failure(ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        log.error("Invalid khatian request: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ApiResponseDTO.failure(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleRuntime(RuntimeException ex) {
        log.error("Unexpected khatian service error", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponseDTO.failure("Unable to process request"));
    }

    private String formatFieldError(FieldError error) {
        return error.getDefaultMessage();
    }
}
