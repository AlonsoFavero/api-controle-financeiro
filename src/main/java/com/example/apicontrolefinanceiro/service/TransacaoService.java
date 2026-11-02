package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.dto.ResumoFinanceiroDTO;
import com.example.apicontrolefinanceiro.model.Categoria;
import com.example.apicontrolefinanceiro.model.TipoTransacao;
import com.example.apicontrolefinanceiro.model.Transacao;
import com.example.apicontrolefinanceiro.repository.CategoriaRepository;
import com.example.apicontrolefinanceiro.repository.TransacaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final CategoriaRepository categoriaRepository;

    public TransacaoService(TransacaoRepository transacaoRepository, CategoriaRepository categoriaRepository) {

        this.transacaoRepository = transacaoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Transacao salvar(Transacao transacao) {

        return transacaoRepository.save(transacao);
    }

    public List<Transacao> listar() {

        return transacaoRepository.findAll();
    }

    public Optional<Transacao> buscarPorId(Long id) {

        return transacaoRepository.findById(id);
    }

    public void excluirPorId(Long id) {

        buscarPorId(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        transacaoRepository.deleteById(id);
    }

    public Transacao atualizar(Long id, Transacao novosDados) {

        Transacao transacao = buscarPorId(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        transacao.setDescricao(novosDados.getDescricao());
        transacao.setData(novosDados.getData());
        transacao.setTipo(novosDados.getTipo());
        transacao.setValor(novosDados.getValor());

        return transacaoRepository.save(transacao);
    }

    public BigDecimal calcularSaldo() {

        BigDecimal saldo = BigDecimal.ZERO;

        for (Transacao transacao : listar()) {

            if (transacao.getTipo() == TipoTransacao.RECEITA) {

                saldo = saldo.add(transacao.getValor());
            } else {

                saldo = saldo.subtract(transacao.getValor());
            }

        }

        return saldo;
    }

    public List<Transacao> buscarPorTipo(TipoTransacao tipo) {

        return transacaoRepository.findByTipo(tipo);
    }

    public BigDecimal calcularTotalReceitas() {

        BigDecimal saldo = BigDecimal.ZERO;

        for (Transacao transacao : listar()) {

            if (transacao.getTipo() == TipoTransacao.RECEITA) {

                saldo = saldo.add(transacao.getValor());
            }

        }
        return saldo;
    }

    public BigDecimal calcularTotalDespesas() {

        BigDecimal saldo = BigDecimal.ZERO;

        for (Transacao transacao : listar()) {

            if (transacao.getTipo() == TipoTransacao.DESPESA) {

                saldo = saldo.add(transacao.getValor());
            }

        }
        return saldo;
    }

    public List<Transacao> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {

        if (inicio.isAfter(fim)) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return transacaoRepository.findByDataBetween(inicio, fim);
    }

    public BigDecimal calcularReceitasPorPeriodo(LocalDate inicio, LocalDate fim) {

        BigDecimal saldo = BigDecimal.ZERO;

        for (Transacao transacao : buscarPorPeriodo(inicio, fim)) {

            if (transacao.getTipo() == TipoTransacao.RECEITA) {

                saldo = saldo.add(transacao.getValor());
            }

        }
        return saldo;
    }


    public BigDecimal calcularDespesasPorPeriodo(LocalDate inicio, LocalDate fim) {

        BigDecimal saldo = BigDecimal.ZERO;

        for (Transacao transacao : buscarPorPeriodo(inicio, fim)) {

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

    public ResumoFinanceiroDTO calcularResumoPorPeriodo(LocalDate inicio, LocalDate fim) {

        BigDecimal receitas = calcularReceitasPorPeriodo(inicio, fim);
        BigDecimal despesas = calcularDespesasPorPeriodo(inicio, fim);
        BigDecimal saldo = calcularSaldoPorPeriodo(inicio, fim);


        return new ResumoFinanceiroDTO(receitas, despesas, saldo);
    }

    public List<Transacao> listarPorDataDesc() {

        return transacaoRepository.findAllByOrderByDataDesc();
    }

    public List<Transacao> buscarPorFaixaDeValor(BigDecimal valorMinimo, BigDecimal valorMaximo) {

        if (valorMinimo.compareTo(valorMaximo) > 0) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return transacaoRepository.findAllByValorBetween(valorMinimo, valorMaximo);
    }

    public Page<Transacao> listarPaginado(Pageable pageable) {

        return transacaoRepository.findAll(pageable);
    }

    public Categoria buscarCategoriaPorId(Long id) {

       return categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

    }
}
