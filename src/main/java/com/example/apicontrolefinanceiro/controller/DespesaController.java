
package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.model.Despesa;
import com.example.apicontrolefinanceiro.service.DespesaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Despesas", description = "Endpoints para gerenciamento de despesas")
@RestController
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService) {
        this.despesaService = despesaService;
    }

    @GetMapping
    @Operation(summary = "Lista de todas as despesas", description = "Retorna as despesas cadastradas com paginação")
    @ApiResponse(responseCode = "200", description = "Despesas listadas com sucesso")
    public Page<Despesa> listar(Pageable pageable) {
        return despesaService.listar(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cadastra uma nova despesa", description = "Recebe os dados e registra uma nova despesa")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Despesa cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public Despesa salvar(@RequestBody @Valid Despesa despesa) {
        return despesaService.salvar(despesa);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma despesa por ID", description = "Retorna os dados de uma despesa específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesa encontrada"),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
    })
    public Despesa buscarPorId(@PathVariable Long id) {
        return despesaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma despesa", description = "Atualiza os dados de uma despesa existente pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Despesa ou categoria não encontrada")
    })
    public Despesa atualizar(@PathVariable Long id, @RequestBody @Valid Despesa dados) {
        return despesaService.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma despesa", description = "Remove uma despesa pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Despesa excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Despesa não encontrada")
    })
    public void excluir(@PathVariable Long id) {
        despesaService.excluir(id);
    }

    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Lista despesas por categoria", description = "Retorna todas as despesas associadas a uma categoria específica")
    @ApiResponse(responseCode = "200", description = "Despesas listadas com sucesso")
    public List<Despesa> listarPorCategoria(@PathVariable Long categoriaId) {
        return despesaService.buscarPorCategoria(categoriaId);
    }

    @GetMapping("/periodo")
    @Operation(summary = "Filtra despesas por período", description = "Retorna despesas dentro de um intervalo de datas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesas filtradas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Intervalo de datas inválido")
    })
    public Page<Despesa> listarPorPeriodo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim, Pageable pageable) {

        if (inicio.isAfter(fim)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data inicial não pode ser posterior a data final");
        }

        return despesaService.buscarPorPeriodo(inicio, fim, pageable);
    }

    @GetMapping("/valor")
    @Operation(summary = "Filtra despesas por faixa de valor", description = "Retorna despesas entre um valor mínimo e máximo")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesas filtradas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Faixa de valores inválida")
    })
    public Page<Despesa> listarPorFaixaDeValor(@RequestParam BigDecimal valorMinimo, @RequestParam BigDecimal valorMaximo, Pageable pageable) {

        if (valorMinimo.compareTo(valorMaximo) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "valor minimo não pode ser maior que o valor maximo");
        }

        return despesaService.buscarPorFaixaDeValor(valorMinimo, valorMaximo, pageable);
    }

    @GetMapping("/categoria-valor")
    @Operation(summary = "Filtra despesas por categoria e valor", description = "Retorna despesas de uma categoria dentro de uma faixa de valor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesas filtradas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Faixa de valores inválida"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public Page<Despesa> buscarPorCategoriaEFaixaDeValor(@RequestParam Long categoriaId, @RequestParam BigDecimal valorMinimo, @RequestParam BigDecimal valorMaximo, Pageable pageable) {

        if (valorMinimo.compareTo(valorMaximo) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "valor minimo não pode ser maior que o valor maximo");
        }

        return despesaService.buscarPorCategoriaEFaixaDeValor(categoriaId, valorMinimo, valorMaximo, pageable);
    }

    @GetMapping("/categoria-periodo")
    @Operation(summary = "Filtra despesas por categoria e período", description = "Retorna despesas de uma categoria dentro de um intervalo de datas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Despesas filtradas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Intervalo de datas inválido"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public Page<Despesa> buscarPorCategoriaEPeriodo(@RequestParam Long categoriaId, @RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal, Pageable pageable) {

        if (dataInicial.isAfter(dataFinal)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "data inicial não pode ser posterior a data final");
        }

        return despesaService.buscarPorCategoriaEPeriodo(categoriaId, dataInicial, dataFinal, pageable);
    }
}
