package com.tp.backend.exception;
import com.tp.backend.dto.exceptions.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        ApiError body = new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now(), getStackTraceAsString(ex));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(NotFoundException ex) {
        ApiError body = new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now(), getStackTraceAsString(ex));
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    // SOLO para errores inesperados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        ex.printStackTrace();
        ApiError body = new ApiError("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR, ZonedDateTime.now(), getStackTraceAsString(ex));
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getStackTraceAsString(Throwable ex) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }
}
