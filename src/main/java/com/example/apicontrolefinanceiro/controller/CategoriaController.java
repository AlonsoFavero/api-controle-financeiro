package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.dto.CategoriaRequestDTO;
import com.example.apicontrolefinanceiro.dto.CategoriaResponseDTO;
import com.example.apicontrolefinanceiro.model.Categoria;
import com.example.apicontrolefinanceiro.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){

        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaResponseDTO> listar(){

           return categoriaService.listar()
                .stream()
                .map(categoria -> {
                    CategoriaResponseDTO dto = new CategoriaResponseDTO();

                       dto.setId(categoria.getId());
                       dto.setNome(categoria.getNome());

                    return dto;
                        })
                   .toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Categoria salvar(@RequestBody @Valid CategoriaRequestDTO dados){

        Categoria categoria = new Categoria();
        categoria.setNome(dados.getNome());

        return categoriaService.salvar(categoria);
    }
}
