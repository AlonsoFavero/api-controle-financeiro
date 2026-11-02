package com.example.apicontrolefinanceiro.repository;

import com.example.apicontrolefinanceiro.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
