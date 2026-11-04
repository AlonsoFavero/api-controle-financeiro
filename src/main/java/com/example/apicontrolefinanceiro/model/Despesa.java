package com.example.apicontrolefinanceiro.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
