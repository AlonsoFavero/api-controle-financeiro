package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.model.Categoria;
import com.example.apicontrolefinanceiro.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Categoria atualizar(Long id, String nome){

        Categoria categoria = buscarPorId(id);
        categoria.setNome(nome);
        return categoriaRepository.save(categoria);
    }

    public void excluir(Long id){

        Categoria categoria = buscarPorId(id);
         categoriaRepository.delete(categoria);
    }
}
