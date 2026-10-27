package com.example.apicontrolefinanceiro.dto;

import com.example.apicontrolefinanceiro.model.TipoTransacao;
import com.example.apicontrolefinanceiro.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoResponseDTO {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private TipoTransacao tipo;

    public TransacaoResponseDTO(Transacao transacao){

        this.id = transacao.getId();
        this.data = transacao.getData();
        this.descricao = transacao.getDescricao();
        this.tipo = transacao.getTipo();
        this.valor = transacao.getValor();
    }
}
