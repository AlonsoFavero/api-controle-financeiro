
package com.example.apicontrolefinanceiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Dados de resposta de uma receita")
public class ReceitaResponseDTO {

    @Schema(description = "Identificador da receita", example = "1")
    private Long id;

    @Schema(description = "Descrição da receita", example = "Salário mensal")
    private String descricao;

    @Schema(description = "Valor da receita", example = "5000.00")
    private BigDecimal valor;

    @Schema(description = "Data em que a receita foi recebida", example = "2026-11-19")
    private LocalDate data;

    public ReceitaResponseDTO() {
    }

    public ReceitaResponseDTO(Long id, String descricao, BigDecimal valor, LocalDate data) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
