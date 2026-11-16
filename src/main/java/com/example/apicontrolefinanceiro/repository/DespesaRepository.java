package com.example.apicontrolefinanceiro.repository;

import com.example.apicontrolefinanceiro.model.Despesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByCategoriaId(Long categoriaId);
    Page<Despesa> findByDataBetween(LocalDate inicio, LocalDate fim, Pageable pageable);
    List<Despesa> findByValorBetween(BigDecimal valorMinimo, BigDecimal valorMaximo);
    Page<Despesa> findByCategoriaIdAndValorBetween(Long categoriaId, BigDecimal valorMinimo, BigDecimal valorMaximo, Pageable pageable);
    Page<Despesa> findByCategoriaIdAndDataBetween(Long categoriaId, LocalDate dataInicial, LocalDate dataFinal, Pageable pageable);


}
