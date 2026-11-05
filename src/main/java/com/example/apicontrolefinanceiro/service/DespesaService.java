package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.repository.DespesaRepository;
import org.springframework.stereotype.Service;

@Service
public class DespesaService {

    private final DespesaRepository despesaRepository;

    public DespesaService(DespesaRepository despesaRepository){

        this.despesaRepository = despesaRepository;
    }
}
