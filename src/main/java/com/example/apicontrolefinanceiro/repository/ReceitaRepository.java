package com.example.apicontrolefinanceiro.repository;

import com.example.apicontrolefinanceiro.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}
