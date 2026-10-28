package com.example.apicontrolefinanceiro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> trataTransacaoNaoEncontrada (ResponseStatusException exception){

        return ResponseEntity.status(exception.getStatusCode()).build();
    }
}
