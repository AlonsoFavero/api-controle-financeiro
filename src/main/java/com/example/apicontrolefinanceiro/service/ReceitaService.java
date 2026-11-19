
package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.dto.ReceitaRequestDTO;
import com.example.apicontrolefinanceiro.dto.ReceitaResponseDTO;
import com.example.apicontrolefinanceiro.model.Receita;
import com.example.apicontrolefinanceiro.repository.ReceitaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ReceitaService {

    private final ReceitaRepository receitaRepository;

    public ReceitaService(ReceitaRepository receitaRepository) {
        this.receitaRepository = receitaRepository;
    }

    public ReceitaResponseDTO salvar(ReceitaRequestDTO dados) {
        Receita receita = new Receita();
        receita.setDescricao(dados.getDescricao());
        receita.setData(dados.getData());
        receita.setValor(dados.getValor());

        return converterParaDTO(receitaRepository.save(receita));
    }

    public Page<ReceitaResponseDTO> listar(Pageable pageable) {
        return receitaRepository.findAll(pageable)
                .map(this::converterParaDTO);
    }

    public ReceitaResponseDTO buscarPorId(Long id) {
        return converterParaDTO(buscarEntidadePorId(id));
    }

    public ReceitaResponseDTO atualizar(Long id, ReceitaRequestDTO novosDados) {
        Receita receita = buscarEntidadePorId(id);

        receita.setDescricao(novosDados.getDescricao());
        receita.setData(novosDados.getData());
        receita.setValor(novosDados.getValor());

        return converterParaDTO(receitaRepository.save(receita));
    }

    public void excluirPorId(Long id) {
        buscarEntidadePorId(id);
        receitaRepository.deleteById(id);
    }

    public Page<ReceitaResponseDTO> listarPorPeriodo(
            LocalDate inicio, LocalDate fim, Pageable pageable) {

        return receitaRepository.findByDataBetween(inicio, fim, pageable)
                .map(this::converterParaDTO);
    }

    public Page<ReceitaResponseDTO> listarPorValor(
            BigDecimal valorMinimo, BigDecimal valorMaximo, Pageable pageable) {

        return receitaRepository.findByValorBetween(valorMinimo, valorMaximo, pageable)
                .map(this::converterParaDTO);
    }

    public Page<ReceitaResponseDTO> listarPorValorEPeriodo(
            BigDecimal valorMinimo, BigDecimal valorMaximo,
            LocalDate inicio, LocalDate fim, Pageable pageable) {

        return receitaRepository.findByValorBetweenAndDataBetween(
                        valorMinimo, valorMaximo, inicio, fim, pageable)
                .map(this::converterParaDTO);
    }

    public BigDecimal somarReceitasPorPeriodo(LocalDate inicio, LocalDate fim) {
        return receitaRepository.somarReceitasPorPeriodo(inicio, fim);
    }

    private Receita buscarEntidadePorId(Long id) {
        return receitaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Receita não encontrada"));
    }

    private ReceitaResponseDTO converterParaDTO(Receita receita) {
        return new ReceitaResponseDTO(
                receita.getId(),
                receita.getDescricao(),
                receita.getValor(),
                receita.getData()
        );
    }
}
