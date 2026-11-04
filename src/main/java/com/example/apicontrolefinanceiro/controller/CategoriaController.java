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
    public CategoriaResponseDTO salvar(@RequestBody @Valid CategoriaRequestDTO dados){

        Categoria categoria = new Categoria();
        categoria.setNome(dados.getNome());

        Categoria categoriaSalva = categoriaService.salvar(categoria);

        CategoriaResponseDTO dto = new CategoriaResponseDTO();

        dto.setId(categoriaSalva.getId());
        dto.setNome(categoriaSalva.getNome());

        return dto;
    }

    @GetMapping("/{id}")
    public CategoriaResponseDTO buscarPorId(@PathVariable Long id){

        Categoria categoriaBuscada = categoriaService.buscarPorId(id);

        CategoriaResponseDTO dto = new CategoriaResponseDTO();

        dto.setId(categoriaBuscada.getId());
        dto.setNome(categoriaBuscada.getNome());

        return dto;
    }

    @PutMapping("/{id}")
    public CategoriaResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid CategoriaRequestDTO dados){

       Categoria categoriaAtualizada = categoriaService.atualizar(id, dados.getNome());

       CategoriaResponseDTO dto = new CategoriaResponseDTO();

       dto.setId(categoriaAtualizada.getId());
       dto.setNome(categoriaAtualizada.getNome());

       return dto;

    }
}
