
package com.example.apicontrolefinanceiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class CategoriaRequestDTO {

    @Schema(description = "Nome da categoria", example = "Alimentação")
    @NotBlank
    private String nome;

    public CategoriaRequestDTO(){

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
