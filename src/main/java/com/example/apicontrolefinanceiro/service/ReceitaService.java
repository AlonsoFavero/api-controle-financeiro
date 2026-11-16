package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.model.Receita;
import com.example.apicontrolefinanceiro.repository.ReceitaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;

    public ReceitaService(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }

    public Receita salvar(Receita receita){

        return receitaRepository.save(receita);
    }

    public List<Receita> listar(){

        return receitaRepository.findAll();
    }

    public Optional<Receita> buscarPorId(Long id){

        return receitaRepository.findById(id);
    }

    public Receita atualizar(Long id, Receita novosDados){

        Receita receita = buscarPorId(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        receita.setDescricao(novosDados.getDescricao());
        receita.setData(novosDados.getData());
        receita.setValor(novosDados.getValor());

        return receitaRepository.save(receita);
    }

    public void excluirPorId(Long id) {

        buscarPorId(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        receitaRepository.deleteById(id);
    }
}
