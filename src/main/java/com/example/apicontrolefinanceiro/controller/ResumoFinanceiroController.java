
package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.model.ResumoFinanceiro;
import com.example.apicontrolefinanceiro.service.ResumoFinanceiroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Tag(name = "Resumo Financeiro", description = "Consultas de resumo financeiro")
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
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resumo financeiro gerado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros ou período inválidos")
    })
    public ResumoFinanceiro gerarResumo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {

        return resumoFinanceiroService.gerarResumo(inicio, fim);
    }
}
