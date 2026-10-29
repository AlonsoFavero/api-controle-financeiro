package com.example.apicontrolefinanceiro.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> trataTransacaoNaoEncontrada (ResponseStatusException exception){

        return ResponseEntity.status(exception.getStatusCode())
                .body("Transacao não encontrada");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> trataErroDeValidacao (MethodArgumentNotValidException exception){

        return ResponseEntity.badRequest().body("Dados inválidos");
    }
}
