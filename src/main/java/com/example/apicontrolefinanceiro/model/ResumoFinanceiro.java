
package com.example.apicontrolefinanceiro.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Representa o resumo financeiro de um período")
public class ResumoFinanceiro {

    @Schema(description = "Valor total das receitas", example = "5000.00")
    private BigDecimal totalReceitas;

    @Schema(description = "Valor total das despesas", example = "2000.00")
    private BigDecimal totalDespesas;

    @Schema(description = "Saldo resultante da diferença entre receitas e despesas", example = "3000.00")
    private BigDecimal saldo;

    public ResumoFinanceiro(){

    }

    public ResumoFinanceiro(BigDecimal totalReceitas, BigDecimal totalDespesas, BigDecimal saldo) {
        this.totalReceitas = totalReceitas;
        this.totalDespesas = totalDespesas;
        this.saldo = saldo;
    }

    public BigDecimal getTotalReceitas() {
        return totalReceitas;
    }

    public void setTotalReceitas(BigDecimal totalReceitas) {
        this.totalReceitas = totalReceitas;
    }

    public BigDecimal getTotalDespesas() {
        return totalDespesas;
    }

    public void setTotalDespesas(BigDecimal totalDespesas) {
        this.totalDespesas = totalDespesas;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "ResumoFinanceiro{" +
                "totalReceitas=" + totalReceitas +
                ", totalDespesas=" + totalDespesas +
                ", saldo=" + saldo +
                '}';
    }
}
