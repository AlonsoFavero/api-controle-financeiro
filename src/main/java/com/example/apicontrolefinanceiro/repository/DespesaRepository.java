package com.example.apicontrolefinanceiro.repository;

import com.example.apicontrolefinanceiro.model.Despesa;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {

    List<Despesa> findByCategoriaId(Long categoriaId);
}
