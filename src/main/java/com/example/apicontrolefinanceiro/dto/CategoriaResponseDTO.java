
package com.example.apicontrolefinanceiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CategoriaResponseDTO {

    @Schema(description = "Identificador da categoria", example = "1")
    private Long id;

    @Schema(description = "Nome da categoria", example = "Alimentação")
    private String nome;

    public CategoriaResponseDTO(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
