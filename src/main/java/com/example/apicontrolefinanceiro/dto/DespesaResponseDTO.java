
package com.example.apicontrolefinanceiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Dados de resposta de uma despesa")
public class DespesaResponseDTO {

    @Schema(description = "Identificador da despesa", example = "1")
    private Long id;

    @Schema(description = "Descrição da despesa", example = "Conta de energia")
    private String descricao;

    @Schema(description = "Valor da despesa", example = "250.00")
    private BigDecimal valor;

    @Schema(description = "Data da despesa", example = "2026-11-19")
    private LocalDate data;

    @Schema(description = "Categoria associada à despesa")
    private CategoriaResponseDTO categoria;

    public DespesaResponseDTO() {
    }

    public DespesaResponseDTO(Long id, String descricao, BigDecimal valor, LocalDate data, CategoriaResponseDTO categoria) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
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

    public CategoriaResponseDTO getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaResponseDTO categoria) {
        this.categoria = categoria;
    }
}
