package com.example.apicontrolefinanceiro.dto;

import com.example.apicontrolefinanceiro.model.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransacaoResponseDTO {

    private Long id;
    private String descricao;
    private BigDecimal valor;
    private LocalDate data;
    private TipoTransacao tipo;
}
