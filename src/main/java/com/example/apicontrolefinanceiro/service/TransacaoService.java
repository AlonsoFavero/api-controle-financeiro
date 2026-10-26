package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.model.Transacao;
import com.example.apicontrolefinanceiro.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

@Service
public class TransacaoService {

    private TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository){

        this.transacaoRepository = transacaoRepository;
    }

    public Transacao salvar(Transacao transacao){

        return transacaoRepository.save(transacao);
    }
}
