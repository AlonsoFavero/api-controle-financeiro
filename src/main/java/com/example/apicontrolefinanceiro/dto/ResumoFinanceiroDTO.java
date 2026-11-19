
package com.example.apicontrolefinanceiro.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public class ResumoFinanceiroDTO {

    @Schema(description = "Valor total das receitas no período", example = "5000.00")
    private BigDecimal totalReceitas;

    @Schema(description = "Valor total das despesas no período", example = "2000.00")
    private BigDecimal totalDespesas;

    @Schema(description = "Saldo resultante da diferença entre receitas e despesas", example = "3000.00")
    private BigDecimal saldo;

    public ResumoFinanceiroDTO(BigDecimal totalReceitas, BigDecimal totalDespesas, BigDecimal saldo){

        this.totalReceitas = totalReceitas;
        this.totalDespesas = totalDespesas;
        this.saldo = saldo;
    }

    public BigDecimal getTotalReceitas() {
        return totalReceitas;
    }

    public BigDecimal getTotalDespesas() {
        return totalDespesas;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}
