package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.service.CategoriaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){

        this.categoriaService = categoriaService;
    }
}
