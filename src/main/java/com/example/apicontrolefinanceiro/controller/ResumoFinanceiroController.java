package com.example.apicontrolefinanceiro.controller;


import com.example.apicontrolefinanceiro.model.ResumoFinanceiro;
import com.example.apicontrolefinanceiro.service.ResumoFinanceiroService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/resumo")
public class ResumoFinanceiroController {

    private final ResumoFinanceiroService resumoFinanceiroService;

    public ResumoFinanceiroController(ResumoFinanceiroService resumoFinanceiroService) {
        this.resumoFinanceiroService = resumoFinanceiroService;
    }

@GetMapping
    public ResumoFinanceiro gerarResumo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim){

        return resumoFinanceiroService.gerarResumo(inicio, fim);
    }
}
