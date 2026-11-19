
package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.dto.CategoriaRequestDTO;
import com.example.apicontrolefinanceiro.dto.CategoriaResponseDTO;
import com.example.apicontrolefinanceiro.model.Categoria;
import com.example.apicontrolefinanceiro.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(name = "Categorias", description = "Gerenciamento de categorias")
@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService){

        this.categoriaService = categoriaService;
    }

    @GetMapping
    @Operation(summary = "Lista as categorias", description = "Retorna todas as categorias cadastradas")
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
    @Operation(summary = "Cadastra uma categoria", description = "Recebe os dados e registra uma nova categoria")
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
    @Operation(summary = "Busca uma categoria por ID", description = "Retorna os dados de uma categoria específica")
    public CategoriaResponseDTO buscarPorId(@PathVariable Long id){

        Categoria categoriaBuscada = categoriaService.buscarPorId(id);

        CategoriaResponseDTO dto = new CategoriaResponseDTO();

        dto.setId(categoriaBuscada.getId());
        dto.setNome(categoriaBuscada.getNome());

        return dto;
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma categoria", description = "Atualiza o nome de uma categoria existente pelo ID")
    public CategoriaResponseDTO atualizar(@PathVariable Long id, @RequestBody @Valid CategoriaRequestDTO dados){

        Categoria categoriaAtualizada = categoriaService.atualizar(id, dados.getNome());

        CategoriaResponseDTO dto = new CategoriaResponseDTO();

        dto.setId(categoriaAtualizada.getId());
        dto.setNome(categoriaAtualizada.getNome());

        return dto;

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma categoria", description = "Remove uma categoria pelo ID, desde que não esteja vinculada a despesas")
    public void excluir(@PathVariable Long id){

        categoriaService.excluir(id);
    }
}
