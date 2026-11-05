package com.example.apicontrolefinanceiro.repository;

import com.example.apicontrolefinanceiro.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

}
