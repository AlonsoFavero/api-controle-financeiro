package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.dto.TransacaoResponseDTO;
import com.example.apicontrolefinanceiro.model.Transacao;
import com.example.apicontrolefinanceiro.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService){

        this.transacaoService = transacaoService;
    }

    @PostMapping
    public TransacaoResponseDTO salvar(@Valid @RequestBody Transacao transacao){

        return new TransacaoResponseDTO(transacaoService.salvar(transacao));
    }

    @GetMapping
    public List<TransacaoResponseDTO> listar(){

       return transacaoService.listar()
               .stream()
               .map(transacao -> new TransacaoResponseDTO(transacao))
               .toList();
    }

    @GetMapping("/{id}")
    public Transacao buscarPorId(@PathVariable Long id){

       return transacaoService.buscarPorId(id)
               .orElseThrow(() ->
                       new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void excluirPorId(@PathVariable Long id){

         transacaoService.excluirPorId(id);
    }

    @PutMapping("/{id}")
    public Transacao atualizar(@PathVariable Long id, @Valid @RequestBody Transacao novosDados){

        return transacaoService.atualizar(id,novosDados);
    }
}
