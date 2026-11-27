package com.example.apicontrolefinanceiro.repository;

import com.example.apicontrolefinanceiro.model.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    Page<Receita> findByDataBetween(LocalDate inicio, LocalDate fim, Pageable pageable);
    Page<Receita> findByValorBetween(BigDecimal valorMinimo, BigDecimal valorMaximo, Pageable pageable);
    Page<Receita> findByValorBetweenAndDataBetween(BigDecimal valorMinimo, BigDecimal valorMaximo, LocalDate inicio, LocalDate fim, Pageable pageable);
    Page<Receita> findByUsuarioId(Long usuarioId, Pageable pageable);
    Page<Receita> findByDataBetweenAndUsuarioId(
            LocalDate inicio,
            LocalDate fim,
            Long usuarioId,
            Pageable pageable
    );

    @Query("SELECT COALESCE(SUM(r.valor), 0) FROM Receita r WHERE r.data BETWEEN :inicio AND :fim")
    BigDecimal somarReceitasPorPeriodo(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim
    );
}
