package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.model.ResumoFinanceiro;
import com.example.apicontrolefinanceiro.repository.DespesaRepository;
import com.example.apicontrolefinanceiro.repository.ReceitaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ResumoFinanceiroService {

    private final ReceitaRepository receitaRepository;
    private final DespesaRepository despesaRepository;

    public ResumoFinanceiroService(ReceitaRepository receitaRepository, DespesaRepository despesaRepository) {
        this.receitaRepository = receitaRepository;
        this.despesaRepository = despesaRepository;
    }

    public ResumoFinanceiro gerarResumo(LocalDate inicio, LocalDate fim){

      BigDecimal totalReceitas = receitaRepository.somarReceitasPorPeriodo(inicio, fim);

       BigDecimal totalDespesas = despesaRepository.somarDespesasPorPeriodo(inicio, fim);

       BigDecimal saldo = totalReceitas.subtract(totalDespesas);

        return new ResumoFinanceiro(totalReceitas, totalDespesas, saldo);
    }
}
