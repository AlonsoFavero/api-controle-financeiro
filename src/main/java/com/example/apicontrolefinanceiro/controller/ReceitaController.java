package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.model.Receita;
import com.example.apicontrolefinanceiro.service.ReceitaService;
import io.swagger.v3.oas.annotations.Operation;
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
    public Receita salvar(@RequestBody @Valid Receita receita){

        return receitaService.salvar(receita);
    }

    @GetMapping
    @Operation(summary = "Lista as receitas", description = "Retorna as receitas cadastradas com paginação")
    public Page<Receita> listar(Pageable pageable){

        return receitaService.listar(pageable);
    }

    @GetMapping("{id}")
    @Operation(summary = "Busca uma receita por ID", description = "Retorna os dados de uma receita especifica")
    public Receita buscarPorId(@PathVariable Long id){

        return receitaService.buscarPorId(id);
    }

    @PutMapping("{id}")
    @Operation(summary = "Atualiza uma receita", description = "Atualiza os dados de uma receita existente pelo ID")
    public Receita atualizar(@PathVariable Long id, @RequestBody @Valid Receita dados){

        return receitaService.atualizar(id, dados);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma receita", description = "Remove uma receita pelo ID")
    public void excluir(@PathVariable Long id){

        receitaService.excluirPorId(id);
    }

    @GetMapping("/periodo")
    @Operation(summary = "Filtra receitas por periodo", description = "Retorna uma lista paginada de receitas cadastradas entre as datas inicial e final informadas")
    public Page<Receita> listarPeriodo(LocalDate inicio, LocalDate fim, Pageable pageable){

        return receitaService.listarPorPeriodo(inicio, fim, pageable);
    }

    @GetMapping("/valor")
    @Operation(summary = "Filtra receitas por faixa de valor", description = "Retorna uma lista paginada de receitas entre os valores minimo e maximo informados")
    public Page<Receita> listarValor(BigDecimal valorMinimo, BigDecimal valorMaximo, Pageable pageable){

        return receitaService.listarPorValor(valorMinimo, valorMaximo, pageable);
    }

    @GetMapping("/filtros")
    @Operation(summary = "Filtra receitas por valor e periodo", description = "Retorna uma lista paginada de receitas que atendem simultaneamente aos criterios de faixa de valor e intervalo de datas ")
    public Page<Receita> listarValorEPeriodo(BigDecimal valorMinimo, BigDecimal valorMaximo, LocalDate inicio, LocalDate fim, Pageable pageable){

        return receitaService.listarPorValorEPeriodo(valorMinimo, valorMaximo, inicio, fim, pageable);
    }
}