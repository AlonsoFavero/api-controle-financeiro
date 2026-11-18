package com.example.apicontrolefinanceiro.repository;

import com.example.apicontrolefinanceiro.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    Page<Receita> findByDataBetween(LocalDate inicio, LocalDate fim, Pageable pageable);
    Page<Receita> findByValorBetween(BigDecimal valorMinimo, BigDecimal valorMaximo, Pageable pageable);
}
