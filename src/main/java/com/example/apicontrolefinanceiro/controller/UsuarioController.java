
package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.dto.UsuarioRequestDTO;
import com.example.apicontrolefinanceiro.dto.UsuarioResponseDTO;
import com.example.apicontrolefinanceiro.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Cadastro e gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cadastra um usuário",
            description = "Cadastra um novo usuário com senha criptografada"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
    })
    public UsuarioResponseDTO cadastrar(@Valid @RequestBody UsuarioRequestDTO dados) {

        return usuarioService.cadastrar(dados);
    }
}
