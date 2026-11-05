package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.service.DespesaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService){

        this.despesaService = despesaService;
    }
}
