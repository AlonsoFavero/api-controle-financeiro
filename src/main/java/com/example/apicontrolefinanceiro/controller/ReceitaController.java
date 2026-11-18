package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.model.Receita;
import com.example.apicontrolefinanceiro.service.ReceitaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@RestController
@RequestMapping("/receitas")
public class ReceitaController {

    private final ReceitaService receitaService;

    public ReceitaController(ReceitaService receitaService) {
        this.receitaService = receitaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Receita salvar(@RequestBody @Valid Receita receita){

        return receitaService.salvar(receita);
    }

    @GetMapping
    public Page<Receita> listar(Pageable pageable){

        return receitaService.listar(pageable);
    }

    @GetMapping("{id}")
    public Receita buscarPorId(@PathVariable Long id){

        return receitaService.buscarPorId(id);
    }

    @PutMapping("{id}")
    public Receita atualizar(@PathVariable Long id, @RequestBody @Valid Receita dados){

        return receitaService.atualizar(id, dados);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id){

        receitaService.excluirPorId(id);
    }

    @GetMapping("/periodo")
    public Page<Receita> listarPeriodo(LocalDate inicio, LocalDate fim, Pageable pageable){

        return receitaService.listarPorPeriodo(inicio, fim, pageable);
    }

    @GetMapping("/valor")
    public Page<Receita> listarValor(BigDecimal valorMinimo, BigDecimal valorMaximo, Pageable pageable){

        return receitaService.listarPorValor(valorMinimo, valorMaximo, pageable);
    }
}
