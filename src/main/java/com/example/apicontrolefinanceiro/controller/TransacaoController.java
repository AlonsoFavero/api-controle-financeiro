package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.model.Transacao;
import com.example.apicontrolefinanceiro.service.TransacaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
