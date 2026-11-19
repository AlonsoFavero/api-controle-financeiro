
package com.example.apicontrolefinanceiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DespesaRequestDTO {

    @Schema(description = "Descrição da despesa", example = "Conta de energia")
    @NotBlank
    private String descricao;

    @Schema(description = "Valor da despesa, deve ser positivo", example = "250.00")
    @NotNull
    @Positive
    private BigDecimal valor;

    @Schema(description = "Data da despesa", example = "2026-11-19")
    @NotNull
    private LocalDate data;

    @Schema(description = "ID da categoria associada", example = "2")
    @NotNull
    private Long categoriaId;

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

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }
}
