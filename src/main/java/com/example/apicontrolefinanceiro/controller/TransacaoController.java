package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.model.Transacao;
import com.example.apicontrolefinanceiro.service.TransacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService){

        this.transacaoService = transacaoService;
    }

    @PostMapping
    public Transacao salvar(@RequestBody Transacao transacao){

      return transacaoService.salvar(transacao);
    }

    @GetMapping
    public List<Transacao> listar(){

        return transacaoService.listar();
    }

    @GetMapping("/{id}")
    public Transacao buscarPorId(@PathVariable Long id){

       return transacaoService.buscarPorId(id)
               .orElseThrow(() ->
                       new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
