package com.project.routes.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(ResponseStatusException ex) {
        Map<String, String> body = new HashMap<>();
        body.put("status code", String.valueOf(ex.getRawStatusCode()));
        body.put("error", ex.getReason());
        return ResponseEntity.status(ex.getRawStatusCode()).body(body);
    }
}