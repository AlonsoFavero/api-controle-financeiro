package com.example.apicontrolefinanceiro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> trataTransacaoNaoEncontrada (ResponseStatusException exception){

       if(exception.getStatusCode().equals(HttpStatus.NOT_FOUND)){

           return ResponseEntity.status(exception.getStatusCode())
                   .body("Despesa não encontrada");
       }

       return ResponseEntity.status(exception.getStatusCode())
               .body("Erro na requisição");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> trataErroDeValidacao (MethodArgumentNotValidException exception){

        String mensagem = exception.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.badRequest().body(mensagem);
    }
}
