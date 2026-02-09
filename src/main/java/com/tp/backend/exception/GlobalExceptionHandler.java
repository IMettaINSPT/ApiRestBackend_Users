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
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;
import java.util.Map;
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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("Invalid JSON {} {} -> 400: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                buildError(
                        "JSON inválido o mal formado (revisá formatos de fecha/enum/números).",
                        HttpStatus.BAD_REQUEST,
                        ex,
                        "INVALID_JSON",
                        Map.of()
                )
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        log.warn("Type mismatch {} {} -> 400: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                buildError(
                        "Parámetro inválido: " + ex.getName(),
                        HttpStatus.BAD_REQUEST,
                        ex,
                        "ARGUMENT_TYPE_MISMATCH",
                        Map.of()
                )
        );
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        log.warn("Method not supported {} {} -> 405", request.getMethod(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(
                buildError(
                        "Método no permitido para este endpoint.",
                        HttpStatus.METHOD_NOT_ALLOWED,
                        ex,
                        "METHOD_NOT_ALLOWED",
                        Map.of()
                )
        );
    }

    // 3) Validaciones @Valid (DTOs) - devuelve fieldErrors (clave para UX en el front)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fe -> fe.getDefaultMessage() != null ? fe.getDefaultMessage() : "inválido",
                        (a, b) -> a // si hay duplicados, nos quedamos con el primero
                ));

        String msg = fieldErrors.entrySet().stream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(Collectors.joining("; "));

        log.warn("Validation error {} {} -> 400: {}", request.getMethod(), request.getRequestURI(), msg);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                buildError(
                        "Validación inválida",
                        HttpStatus.BAD_REQUEST,
                        ex,
                        "VALIDATION_ERROR",
                        fieldErrors
                )
        );
    }

    // 4) Errores de request/params que vos tirás hoy (IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        log.warn("Bad request {} {} -> 400: {}", request.getMethod(), request.getRequestURI(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                buildError(
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST,
                        ex,
                        "BAD_REQUEST",
                        Map.of()
                )
        );
    }

    // 5) Forbidden (Spring Security)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex, HttpServletRequest request) {
        log.warn("Access denied {} {} -> 403", request.getMethod(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                buildError(
                        "No tenés permisos para realizar esta acción.",
                        HttpStatus.FORBIDDEN,
                        ex,
                        "FORBIDDEN",
                        Map.of()
                )
        );
    }

    // 6) Fallback: errores inesperados (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest request) {

        // ✅ Si por cualquier motivo Spring te manda acá una ApiException,
        // la resolvemos como corresponde (404/400/etc) y NO como 500.
        if (ex instanceof ApiException apiEx) {
            HttpStatus status = apiEx.getStatus();

            log.warn("GENERIC received ApiException {} {} -> {}: {}",
                    request.getMethod(),
                    request.getRequestURI(),
                    status.value(),
                    apiEx.getMessage()
            );

            return ResponseEntity.status(status).body(
                    buildError(
                            apiEx.getMessage(),
                            status,
                            apiEx,
                            apiEx.getCode() != null ? apiEx.getCode() : status.name(),
                            Map.of()
                    )
            );
        }

        log.error("Unexpected error {} {} -> 500 | exClass={}",
                request.getMethod(),
                request.getRequestURI(),
                ex.getClass().getName(),
                ex
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                buildError(
                        "Error interno del servidor",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ex,
                        "INTERNAL_ERROR",
                        Map.of()
                )
        );
    }


    private String stackTrace(Throwable ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private ApiError buildError(String message,
                                HttpStatus status,
                                Exception ex,
                                String code,
                                Map<String, String> fieldErrors) {
        return new ApiError(
                message,
                status,
                ZonedDateTime.now(),
                includeTrace ? stackTrace(ex) : null,
                code,
                fieldErrors
        );
    }
}
