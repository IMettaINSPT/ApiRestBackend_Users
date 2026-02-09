package com.tp.backend.dto.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Map;

public record ApiError(
        String message,
        HttpStatus status,
        ZonedDateTime timestamp,
        String trace,

        // NUEVO: código estable para el front (y para logs/analytics)
        String code,

        // NUEVO: errores por campo (para pintar inputs)
        Map<String, String> fieldErrors
) {}
