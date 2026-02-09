package com.tp.backend.exception;

import org.springframework.http.HttpStatus;

public abstract class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final String code;

    protected ApiException(String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.code = null;
    }

    protected ApiException(String code, String message, HttpStatus status) {
        super(message);
        this.status = status;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }
}
