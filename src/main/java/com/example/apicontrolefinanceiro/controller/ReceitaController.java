
package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.dto.ReceitaRequestDTO;
import com.example.apicontrolefinanceiro.dto.ReceitaResponseDTO;
import com.example.apicontrolefinanceiro.service.ReceitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Tag(name = "Receitas", description = "Gerenciamento de receitas")
@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma nova receita", description = "Recebe os dados e registra uma nova receita")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Receita cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ReceitaResponseDTO salvar(@RequestBody @Valid ReceitaRequestDTO dados) {
        return receitaService.salvar(dados);
    }

    @GetMapping
    @Operation(summary = "Lista as receitas", description = "Retorna as receitas cadastradas com paginação")
    @ApiResponse(responseCode = "200", description = "Receitas listadas com sucesso")
    public Page<ReceitaResponseDTO> listar(Pageable pageable) {
        return receitaService.listar(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma receita por ID", description = "Retorna os dados de uma receita específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receita encontrada"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada")
    })
    public ReceitaResponseDTO buscarPorId(@PathVariable Long id) {
        return receitaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma receita", description = "Atualiza os dados de uma receita existente pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Receita atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada")
    })
    public ReceitaResponseDTO atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ReceitaRequestDTO dados) {
        return receitaService.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma receita", description = "Remove uma receita pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Receita excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada")
    })
    public void excluir(@PathVariable Long id) {
        receitaService.excluirPorId(id);
    }

    @GetMapping("/periodo")
    @Operation(summary = "Filtra receitas por período", description = "Retorna uma lista paginada de receitas cadastradas entre as datas inicial e final informadas")
    @ApiResponse(responseCode = "200", description = "Receitas filtradas com sucesso")
    public Page<ReceitaResponseDTO> listarPeriodo(
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim,
            Pageable pageable) {
        return receitaService.listarPorPeriodo(inicio, fim, pageable);
    }

    @GetMapping("/valor")
    @Operation(summary = "Filtra receitas por faixa de valor", description = "Retorna uma lista paginada de receitas entre os valores mínimo e máximo informados")
    @ApiResponse(responseCode = "200", description = "Receitas filtradas com sucesso")
    public Page<ReceitaResponseDTO> listarValor(
            @RequestParam BigDecimal valorMinimo,
            @RequestParam BigDecimal valorMaximo,
            Pageable pageable) {
        return receitaService.listarPorValor(valorMinimo, valorMaximo, pageable);
    }

    @GetMapping("/filtros")
    @Operation(summary = "Filtra receitas por valor e período", description = "Retorna uma lista paginada de receitas que atendem simultaneamente aos critérios de faixa de valor e intervalo de datas")
    @ApiResponse(responseCode = "200", description = "Receitas filtradas com sucesso")
    public Page<ReceitaResponseDTO> listarValorEPeriodo(
            @RequestParam BigDecimal valorMinimo,
            @RequestParam BigDecimal valorMaximo,
            @RequestParam LocalDate inicio,
            @RequestParam LocalDate fim,
            Pageable pageable) {
        return receitaService.listarPorValorEPeriodo(
                valorMinimo, valorMaximo, inicio, fim, pageable);
    }
}
