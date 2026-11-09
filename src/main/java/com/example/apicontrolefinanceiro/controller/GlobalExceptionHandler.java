package com.example.apicontrolefinanceiro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<String> tratarErro(ResponseStatusException erro) {
        return ResponseEntity
                .status(erro.getStatusCode())
                .body(erro.getReason());
    }
}
