package com.tp.backend.dto.exceptions;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public record ApiError(
        String message,
        HttpStatus status,
        ZonedDateTime timestamp,
        String trace
) {}
