package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.dto.TransacaoRequestDTO;
import com.example.apicontrolefinanceiro.dto.TransacaoResponseDTO;
import com.example.apicontrolefinanceiro.model.TipoTransacao;
import com.example.apicontrolefinanceiro.model.Transacao;
import com.example.apicontrolefinanceiro.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService){

        this.transacaoService = transacaoService;
    }

    @PostMapping
    public TransacaoResponseDTO salvar(@Valid @RequestBody TransacaoRequestDTO dados){

        Transacao transacao = converterParaEntidade(dados);

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
    public TransacaoResponseDTO buscarPorId(@PathVariable Long id){

       return transacaoService.buscarPorId(id)
               .map(transacao -> new TransacaoResponseDTO(transacao))
               .orElseThrow(() ->
                       new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPorId(@PathVariable Long id){

         transacaoService.excluirPorId(id);
    }

    @PutMapping("/{id}")
    public TransacaoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody TransacaoRequestDTO dados){

        Transacao transacao = converterParaEntidade(dados);

        return new TransacaoResponseDTO( transacaoService.atualizar(id,transacao));
    }

    private Transacao converterParaEntidade( TransacaoRequestDTO dados){

        Transacao transacao = new Transacao();

        transacao.setDescricao(dados.getDescricao());
        transacao.setValor(dados.getValor());
        transacao.setTipo(dados.getTipo());
        transacao.setData(dados.getData());

        return transacao;
    }

    @GetMapping("/saldo")
    public BigDecimal calculaSaldo (){

       return transacaoService.calcularSaldo();
    }

    @GetMapping("/tipo/{tipo}")
    public List<TransacaoResponseDTO> buscarTipo (@PathVariable TipoTransacao tipo){

        return transacaoService.buscarPorTipo(tipo)
                .stream()
                .map(transacao -> new TransacaoResponseDTO(transacao))
                .toList();
    }
}
