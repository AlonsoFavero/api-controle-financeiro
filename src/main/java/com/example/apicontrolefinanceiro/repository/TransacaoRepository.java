package com.example.apicontrolefinanceiro.repository;

import com.example.apicontrolefinanceiro.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao,Long> {
}
