
package com.example.apicontrolefinanceiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Dados para cadastro e atualização de uma receita")
public class ReceitaRequestDTO {

    @Schema(description = "Descrição da receita", example = "Salário mensal")
    @NotBlank
    private String descricao;

    @Schema(description = "Valor da receita, deve ser positivo", example = "5000.00")
    @NotNull
    @Positive
    private BigDecimal valor;

    @Schema(description = "Data em que a receita foi recebida", example = "2026-11-19")
    @NotNull
    private LocalDate data;

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
