package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.model.Despesa;
import com.example.apicontrolefinanceiro.repository.DespesaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DespesaService {

    private final DespesaRepository despesaRepository;

    public DespesaService(DespesaRepository despesaRepository){

        this.despesaRepository = despesaRepository;
    }

    public List<Despesa> listar(){

        return despesaRepository.findAll();
    }

    public Despesa salvar(Despesa despesa){

        return despesaRepository.save(despesa);
    }

    public Despesa buscarPorId(Long id){

        return despesaRepository.findById(id).orElseThrow();
    }

    public Despesa atualizar(Long id, Despesa dados){


        Despesa despesa = buscarPorId(id);
        despesa.setDescricao(dados.getDescricao());
        despesa.setValor(dados.getValor());
        despesa.setData(dados.getData());
        despesa.setCategoria(dados.getCategoria());

        return despesaRepository.save(despesa);
    }
}
