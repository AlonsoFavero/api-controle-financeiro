
package com.example.apicontrolefinanceiro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Schema(description = "Representa uma despesa financeira")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador da despesa", example = "1")
    private Long id;

    @Schema(description = "Descrição da despesa", example = "Conta de energia")
    @NotBlank
    private String descricao;

    @Schema(description = "Valor da despesa", example = "250.00")
    @NotNull
    @Positive
    private BigDecimal valor;

    @Schema(description = "Data da despesa", example = "2026-11-19")
    @NotNull
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    @NotNull
    @Schema(description = "Categoria associada à despesa")
    private Categoria categoria;

    public Despesa(){

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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
