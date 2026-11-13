package com.example.apicontrolefinanceiro.repository;

import com.example.apicontrolefinanceiro.model.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByCategoriaId(Long categoriaId);
    List<Despesa> findByDataBetween(LocalDate inicio, LocalDate fim);
}
