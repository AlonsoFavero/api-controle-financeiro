
package com.example.apicontrolefinanceiro.dto;

import com.example.apicontrolefinanceiro.model.TipoTransacao;
import com.example.apicontrolefinanceiro.model.Transacao;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoResponseDTO {

    @Schema(description = "Identificador da transação", example = "1")
    private Long id;

    @Schema(description = "Descrição da transação", example = "Compra no supermercado")
    private String descricao;

    @Schema(description = "Valor da transação", example = "150.00")
    private BigDecimal valor;

    @Schema(description = "Data em que ocorreu a transação", example = "2026-11-19")
    private LocalDate data;

    @Schema(description = "Tipo da transação", example = "DESPESA")
    private TipoTransacao tipo;

    public TransacaoResponseDTO(Transacao transacao){

        this.id = transacao.getId();
        this.data = transacao.getData();
        this.descricao = transacao.getDescricao();
        this.tipo = transacao.getTipo();
        this.valor = transacao.getValor();
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }
}
