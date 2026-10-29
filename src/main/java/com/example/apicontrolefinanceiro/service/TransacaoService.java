package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.model.Transacao;
import com.example.apicontrolefinanceiro.repository.TransacaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository){

        this.transacaoRepository = transacaoRepository;
    }

    public Transacao salvar(Transacao transacao){

        return transacaoRepository.save(transacao);
    }

    public List<Transacao> listar(){

        return transacaoRepository.findAll();
    }

    public Optional<Transacao> buscarPorId(Long id){

        return transacaoRepository.findById(id);
    }

    public void excluirPorId(Long id){

         buscarPorId(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        transacaoRepository.deleteById(id);
    }

    public Transacao atualizar(Long id , Transacao novosDados){

        Transacao transacao = buscarPorId(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        transacao.setDescricao(novosDados.getDescricao());
        transacao.setData(novosDados.getData());
        transacao.setTipo(novosDados.getTipo());
        transacao.setValor(novosDados.getValor());

       return transacaoRepository.save(transacao);
    }
}
