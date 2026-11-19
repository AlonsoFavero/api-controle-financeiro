
package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.model.ResumoFinanceiro;
import com.example.apicontrolefinanceiro.service.ResumoFinanceiroService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(
            summary = "Gera um resumo financeiro",
            description = "Retorna o resumo financeiro do período entre as datas inicial e final informadas"
    )
    public ResumoFinanceiro gerarResumo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim){

        return resumoFinanceiroService.gerarResumo(inicio, fim);
    }
}
