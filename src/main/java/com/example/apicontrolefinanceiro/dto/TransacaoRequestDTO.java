
package com.example.apicontrolefinanceiro.dto;

import com.example.apicontrolefinanceiro.model.TipoTransacao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoRequestDTO {

    @Schema(description = "Descrição da transação", example = "Compra no supermercado")
    @NotBlank
    private String descricao;

    @Schema(description = "Valor da transação, deve ser positivo", example = "150.00")
    @NotNull
    @Positive
    private BigDecimal valor;

    @Schema(description = "Tipo da transação", example = "DESPESA")
    @NotNull
    private TipoTransacao tipo;

    @Schema(description = "Data em que ocorreu a transação", example = "2026-11-19")
    @NotNull
    private LocalDate data;

    @Schema(description = "ID da categoria associada", example = "1")
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

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
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
