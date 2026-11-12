package com.example.apicontrolefinanceiro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import com.example.apicontrolefinanceiro.exception.ErroResposta;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroResposta> trataTransacaoNaoEncontrada (ResponseStatusException exception){

       if(exception.getStatusCode().equals(HttpStatus.NOT_FOUND)){

           return ResponseEntity.status(exception.getStatusCode())
                   .body( new ErroResposta(404 , "não encontrado", "Despesa não encontrada"));
       }

       return ResponseEntity.status(exception.getStatusCode())
               .body( new ErroResposta(exception.getStatusCode().value(),
        "Erro na requisição", "Erro na requisição"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> trataErroDeValidacao (MethodArgumentNotValidException exception){

        String mensagem = exception.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        ErroResposta erro = new ErroResposta(
                400,
                "Erro de validação",
                mensagem
        );

        return ResponseEntity.badRequest().body(erro);
    }
}
