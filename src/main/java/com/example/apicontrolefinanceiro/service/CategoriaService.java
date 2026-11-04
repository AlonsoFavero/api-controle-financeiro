package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.model.Categoria;
import com.example.apicontrolefinanceiro.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {

        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listar(){

        return categoriaRepository.findAll();
    }

    public Categoria salvar(Categoria categoria){

        return categoriaRepository.save(categoria);
    }

    public Categoria buscarPorId(Long id){

        return categoriaRepository.findById(id).orElseThrow();
    }
}
