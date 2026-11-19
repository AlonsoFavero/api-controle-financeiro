
package com.example.apicontrolefinanceiro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
@Schema(description = "Representa uma categoria financeira")
public class Categoria {

    @Id
    @GeneratedValue
    @Schema(description = "Identificador da categoria", example = "1")
    private Long id;

    @NotBlank
    @Schema(description = "Nome da categoria", example = "Alimentação")
    private String nome;

    public Categoria(){

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
