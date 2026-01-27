package com.tp.backend.exception;

import com.tp.backend.dto.exceptions.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * En prod debería ser false.
     * En dev te puede servir true para depurar.
     */
    @Value("${app.errors.include-trace:false}")
    private boolean includeTrace;

    // 1) Tus excepciones custom (BadRequestException, NotFoundException, etc.)
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApiException(ApiException ex, HttpServletRequest request) {
        HttpStatus status = ex.getStatus();

        // 404/400 suelen ser WARN (no es “error del server”)
        if (status.is4xxClientError()) {
            log.warn("API error {} {} -> {}: {}", request.getMethod(), request.getRequestURI(), status, ex.getMessage());
        } else {
            log.error("API error {} {} -> {}: {}", request.getMethod(), request.getRequestURI(), status, ex.getMessage(), ex);
        }

        return ResponseEntity.status(status).body(
                new ApiError(
                        ex.getMessage(),
                        status,
                        ZonedDateTime.now(),
                        includeTrace ? stackTrace(ex) : null
                )
        );
    }

    // 2) Validaciones @Valid (DTOs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String msg = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining("; "));

        log.warn("Validation error {} {} -> 400: {}", request.getMethod(), request.getRequestURI(), msg);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiError(
                        "Validación inválida: " + msg,
                        HttpStatus.BAD_REQUEST,
                        ZonedDateTime.now(),
                        includeTrace ? stackTrace(ex) : null
                )
        );
    }

    // 3) Errores de request/params que vos tirás hoy (IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("Bad request {} {} -> 400: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiError(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        ZonedDateTime.now(),
                        includeTrace ? stackTrace(ex) : null
                )
        );
    }

    // 4) Forbidden (Spring Security)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        log.warn("Access denied {} {} -> 403", request.getMethod(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ApiError(
                        "No tenés permisos para realizar esta acción.",
                        HttpStatus.FORBIDDEN,
                        ZonedDateTime.now(),
                        includeTrace ? stackTrace(ex) : null
                )
        );
    }

    // 5) Fallback: errores inesperados (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error {} {} -> 500", request.getMethod(), request.getRequestURI(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiError(
                        "Error interno del servidor",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ZonedDateTime.now(),
                        includeTrace ? stackTrace(ex) : null
                )
        );
    }

    private String formatFieldError(FieldError fe) {
        String field = fe.getField();
        String msg = fe.getDefaultMessage();
        return field + ": " + (msg != null ? msg : "inválido");
    }

    private String stackTrace(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
