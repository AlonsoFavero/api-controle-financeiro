package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.dto.UsuarioRequestDTO;
import com.example.apicontrolefinanceiro.dto.UsuarioResponseDTO;
import com.example.apicontrolefinanceiro.service.UsuarioService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public UsuarioResponseDTO cadastrar(@Valid @RequestBody UsuarioRequestDTO dados){

        return usuarioService.cadastrar(dados);
    }
}
