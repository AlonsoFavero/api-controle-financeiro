package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.model.Receita;
import com.example.apicontrolefinanceiro.repository.ReceitaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;

    public ReceitaService(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }

    public Receita salvar(Receita receita){

        return receitaRepository.save(receita);
    }

    public Page<Receita> listar(Pageable pageable){

        return receitaRepository.findAll(pageable);
    }

    public Receita buscarPorId(Long id){

        return receitaRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Receita atualizar(Long id, Receita novosDados){

        Receita receita = buscarPorId(id);

        receita.setDescricao(novosDados.getDescricao());
        receita.setData(novosDados.getData());
        receita.setValor(novosDados.getValor());

        return receitaRepository.save(receita);
    }

    public void excluirPorId(Long id) {

        buscarPorId(id);

        receitaRepository.deleteById(id);
    }

    public Page<Receita> listarPorPeriodo(LocalDate inicio, LocalDate fim, Pageable pageable){

        return receitaRepository.findByDataBetween(inicio, fim, pageable);
    }
}
