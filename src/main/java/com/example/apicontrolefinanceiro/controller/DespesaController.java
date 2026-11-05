package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.model.Despesa;
import com.example.apicontrolefinanceiro.service.DespesaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService){

        this.despesaService = despesaService;
    }

    @GetMapping
    public List<Despesa> listar(){

        return despesaService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Despesa salvar(@RequestBody Despesa despesa){

        return despesaService.salvar(despesa);
    }

    @GetMapping("/{id}")
    public Despesa buscarPorId(@PathVariable Long id){

        return despesaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Despesa atualizar(@PathVariable Long id, @RequestBody Despesa dados){

        return despesaService.atualizar(id, dados);
    }
}
