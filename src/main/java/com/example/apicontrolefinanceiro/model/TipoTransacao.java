
package com.example.apicontrolefinanceiro.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipo da transação: receita ou despesa")
public enum TipoTransacao {

    @Schema(description = "Representa uma entrada de dinheiro")
    RECEITA,

    @Schema(description = "Representa uma saída de dinheiro")
    DESPESA
}
