package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.model.Categoria;
import com.example.apicontrolefinanceiro.model.Despesa;
import com.example.apicontrolefinanceiro.repository.CategoriaRepository;
import com.example.apicontrolefinanceiro.repository.DespesaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final DespesaRepository despesaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository, DespesaRepository despesaRepository) {

        this.categoriaRepository = categoriaRepository;
        this.despesaRepository = despesaRepository;
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
       List<Despesa> despesas = despesaRepository.findByCategoriaId(id);

        if(!despesas.isEmpty()){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "categoria possui despesas vinculadas");
        }
         categoriaRepository.delete(categoria);
    }
}
