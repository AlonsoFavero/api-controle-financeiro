package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.model.Despesa;
import com.example.apicontrolefinanceiro.repository.DespesaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.apicontrolefinanceiro.repository.CategoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DespesaService {

    private final DespesaRepository despesaRepository;
    private final CategoriaRepository categoriaRepository;

    public DespesaService(DespesaRepository despesaRepository, CategoriaRepository categoriaRepository){

        this.despesaRepository = despesaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Page<Despesa> listar(Pageable pageable){

        return despesaRepository.findAll(pageable);
    }

    public List<Despesa> buscarPorCategoria(Long categoriaId) {

        if (!categoriaRepository.existsById(categoriaId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "categoria não encontrada"
            );
        }

        return despesaRepository.findByCategoriaId(categoriaId);
    }

    public Despesa salvar(Despesa despesa){

      Long categoriaId = despesa.getCategoria().getId();

      if(!categoriaRepository.existsById(categoriaId)){
          throw new ResponseStatusException(
                  HttpStatus.NOT_FOUND, "categoria não encontrada"
          );
      }

      return despesaRepository.save(despesa);
    }

    public Despesa buscarPorId(Long id){

        return despesaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Despesa não encontrada"));
    }

    public Despesa atualizar(Long id, Despesa dados){

        Despesa despesa = buscarPorId(id);
        despesa.setDescricao(dados.getDescricao());
        despesa.setValor(dados.getValor());
        despesa.setData(dados.getData());
        Long categoriaId = dados.getCategoria().getId();

        if(!categoriaRepository.existsById(categoriaId)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "categoria não encontrada"
            );
        }

        despesa.setCategoria(dados.getCategoria());

        return despesaRepository.save(despesa);
    }

    public void excluir(Long id){

        Despesa despesa = buscarPorId(id);
        despesaRepository.delete(despesa);
    }

    public List<Despesa> buscarPorPeriodo(LocalDate inicio, LocalDate fim){

        return despesaRepository.findByDataBetween(inicio, fim);
    }

    public List<Despesa> buscarPorFaixaDeValor(BigDecimal valorMinimo, BigDecimal valorMaximo){

        return despesaRepository.findByValorBetween(valorMinimo, valorMaximo);
    }
}
