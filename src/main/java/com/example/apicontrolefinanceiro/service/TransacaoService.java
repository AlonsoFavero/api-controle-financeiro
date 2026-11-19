
package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.dto.ResumoFinanceiroDTO;
import com.example.apicontrolefinanceiro.dto.TransacaoRequestDTO;
import com.example.apicontrolefinanceiro.dto.TransacaoResponseDTO;
import com.example.apicontrolefinanceiro.model.Categoria;
import com.example.apicontrolefinanceiro.model.TipoTransacao;
import com.example.apicontrolefinanceiro.model.Transacao;
import com.example.apicontrolefinanceiro.repository.CategoriaRepository;
import com.example.apicontrolefinanceiro.repository.TransacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final CategoriaRepository categoriaRepository;

    public TransacaoService(
            TransacaoRepository transacaoRepository,
            CategoriaRepository categoriaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public TransacaoResponseDTO salvar(TransacaoRequestDTO dados) {
        Transacao transacao = new Transacao();

        transacao.setDescricao(dados.getDescricao());
        transacao.setValor(dados.getValor());
        transacao.setTipo(dados.getTipo());
        transacao.setData(dados.getData());
        transacao.setCategoria(buscarCategoriaPorId(dados.getCategoriaId()));

        return converterParaDTO(transacaoRepository.save(transacao));
    }

    public List<TransacaoResponseDTO> listar() {
        return transacaoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public TransacaoResponseDTO buscarPorId(Long id) {
        return converterParaDTO(buscarEntidadePorId(id));
    }

    public void excluirPorId(Long id) {
        buscarEntidadePorId(id);
        transacaoRepository.deleteById(id);
    }

    public TransacaoResponseDTO atualizar(Long id, TransacaoRequestDTO novosDados) {
        Transacao transacao = buscarEntidadePorId(id);

        transacao.setDescricao(novosDados.getDescricao());
        transacao.setData(novosDados.getData());
        transacao.setTipo(novosDados.getTipo());
        transacao.setValor(novosDados.getValor());
        transacao.setCategoria(buscarCategoriaPorId(novosDados.getCategoriaId()));

        return converterParaDTO(transacaoRepository.save(transacao));
    }

    public BigDecimal calcularSaldo() {
        BigDecimal saldo = BigDecimal.ZERO;

        for (Transacao transacao : transacaoRepository.findAll()) {
            if (transacao.getTipo() == TipoTransacao.RECEITA) {
                saldo = saldo.add(transacao.getValor());
            } else {
                saldo = saldo.subtract(transacao.getValor());
            }
        }

        return saldo;
    }

    public List<TransacaoResponseDTO> buscarPorTipo(TipoTransacao tipo) {
        return transacaoRepository.findByTipo(tipo)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public BigDecimal calcularTotalReceitas() {
        BigDecimal saldo = BigDecimal.ZERO;

        for (Transacao transacao : transacaoRepository.findAll()) {
            if (transacao.getTipo() == TipoTransacao.RECEITA) {
                saldo = saldo.add(transacao.getValor());
            }
        }

        return saldo;
    }

    public BigDecimal calcularTotalDespesas() {
        BigDecimal saldo = BigDecimal.ZERO;

        for (Transacao transacao : transacaoRepository.findAll()) {
            if (transacao.getTipo() == TipoTransacao.DESPESA) {
                saldo = saldo.add(transacao.getValor());
            }
        }

        return saldo;
    }

    public List<TransacaoResponseDTO> buscarPorPeriodo(
            LocalDate inicio, LocalDate fim) {

        if (inicio.isAfter(fim)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return transacaoRepository.findByDataBetween(inicio, fim)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public BigDecimal calcularReceitasPorPeriodo(
            LocalDate inicio, LocalDate fim) {

        BigDecimal saldo = BigDecimal.ZERO;

        for (Transacao transacao : transacaoRepository.findByDataBetween(inicio, fim)) {
            if (transacao.getTipo() == TipoTransacao.RECEITA) {
                saldo = saldo.add(transacao.getValor());
            }
        }

        return saldo;
    }

    public BigDecimal calcularDespesasPorPeriodo(
            LocalDate inicio, LocalDate fim) {

        BigDecimal saldo = BigDecimal.ZERO;

        for (Transacao transacao : transacaoRepository.findByDataBetween(inicio, fim)) {
            if (transacao.getTipo() == TipoTransacao.DESPESA) {
                saldo = saldo.add(transacao.getValor());
            }
        }

        return saldo;
    }

    public BigDecimal calcularSaldoPorPeriodo(LocalDate inicio, LocalDate fim) {
        BigDecimal receitas = calcularReceitasPorPeriodo(inicio, fim);
        BigDecimal despesas = calcularDespesasPorPeriodo(inicio, fim);

        return receitas.subtract(despesas);
    }

    public ResumoFinanceiroDTO calcularResumoPorPeriodo(
            LocalDate inicio, LocalDate fim) {

        BigDecimal receitas = calcularReceitasPorPeriodo(inicio, fim);
        BigDecimal despesas = calcularDespesasPorPeriodo(inicio, fim);
        BigDecimal saldo = receitas.subtract(despesas);

        return new ResumoFinanceiroDTO(receitas, despesas, saldo);
    }

    public List<TransacaoResponseDTO> listarPorDataDesc() {
        return transacaoRepository.findAllByOrderByDataDesc()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public List<TransacaoResponseDTO> buscarPorFaixaDeValor(
            BigDecimal valorMinimo, BigDecimal valorMaximo) {

        if (valorMinimo.compareTo(valorMaximo) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return transacaoRepository.findAllByValorBetween(valorMinimo, valorMaximo)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public Page<TransacaoResponseDTO> listarPaginado(Pageable pageable) {
        return transacaoRepository.findAll(pageable)
                .map(this::converterParaDTO);
    }

    public Categoria buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Categoria não encontrada"));
    }

    private Transacao buscarEntidadePorId(Long id) {
        return transacaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Transação não encontrada"));
    }

    private TransacaoResponseDTO converterParaDTO(Transacao transacao) {
        return new TransacaoResponseDTO(transacao);
    }
}
