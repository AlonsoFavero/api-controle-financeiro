package com.example.apicontrolefinanceiro.repository;

import com.example.apicontrolefinanceiro.model.TipoTransacao;
import com.example.apicontrolefinanceiro.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao,Long> {

    List<Transacao> findByTipo(TipoTransacao tipo);
}
