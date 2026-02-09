package com.tp.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NotFoundException extends ApiException {
    public NotFoundException(String message) {

        super(message, HttpStatus.NOT_FOUND);
    }
}
