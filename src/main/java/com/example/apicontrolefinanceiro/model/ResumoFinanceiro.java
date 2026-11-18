package com.example.apicontrolefinanceiro.model;


import java.math.BigDecimal;

public class ResumoFinanceiro {

private BigDecimal totalReceitas;
private BigDecimal totalDespesas;
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
