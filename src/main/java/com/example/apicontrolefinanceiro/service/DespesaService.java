
package com.example.apicontrolefinanceiro.service;

import com.example.apicontrolefinanceiro.dto.CategoriaResponseDTO;
import com.example.apicontrolefinanceiro.dto.DespesaRequestDTO;
import com.example.apicontrolefinanceiro.dto.DespesaResponseDTO;
import com.example.apicontrolefinanceiro.model.Categoria;
import com.example.apicontrolefinanceiro.model.Despesa;
import com.example.apicontrolefinanceiro.model.Usuario;
import com.example.apicontrolefinanceiro.repository.CategoriaRepository;
import com.example.apicontrolefinanceiro.repository.DespesaRepository;
import com.example.apicontrolefinanceiro.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DespesaService {

    private final UsuarioRepository usuarioRepository;
    private final DespesaRepository despesaRepository;
    private final CategoriaRepository categoriaRepository;

    public DespesaService(DespesaRepository despesaRepository,
                          CategoriaRepository categoriaRepository,
                          UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.despesaRepository = despesaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Page<DespesaResponseDTO> listar(Pageable pageable) {
        return despesaRepository.findAll(pageable).map(this::converterParaDTO);
    }

    public List<DespesaResponseDTO> buscarPorCategoria(Long categoriaId) {

        buscarCategoria(categoriaId);

        return despesaRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public DespesaResponseDTO salvar(DespesaRequestDTO dados) {

        Despesa despesa = new Despesa();
        despesa.setDescricao(dados.getDescricao());
        despesa.setValor(dados.getValor());
        despesa.setData(dados.getData());
        despesa.setCategoria(buscarCategoria(dados.getCategoriaId()));
        despesa.setUsuario(buscarUsuarioAutenticado());

        return converterParaDTO(despesaRepository.save(despesa));
    }

    public DespesaResponseDTO buscarPorId(Long id) {
        return converterParaDTO(buscarEntidadePorId(id));
    }

    public DespesaResponseDTO atualizar(Long id, DespesaRequestDTO dados) {

        Despesa despesa = buscarEntidadePorId(id);

        despesa.setDescricao(dados.getDescricao());
        despesa.setValor(dados.getValor());
        despesa.setData(dados.getData());
        despesa.setCategoria(buscarCategoria(dados.getCategoriaId()));

        return converterParaDTO(despesaRepository.save(despesa));
    }

    public void excluir(Long id) {
        Despesa despesa = buscarEntidadePorId(id);
        despesaRepository.delete(despesa);
    }

    public Page<DespesaResponseDTO> buscarPorPeriodo(
            LocalDate inicio, LocalDate fim, Pageable pageable) {

        return despesaRepository.findByDataBetween(inicio, fim, pageable)
                .map(this::converterParaDTO);
    }

    public Page<DespesaResponseDTO> buscarPorFaixaDeValor(
            BigDecimal valorMinimo, BigDecimal valorMaximo, Pageable pageable) {

        return despesaRepository.findByValorBetween(valorMinimo, valorMaximo, pageable)
                .map(this::converterParaDTO);
    }

    public Page<DespesaResponseDTO> buscarPorCategoriaEFaixaDeValor(
            Long categoriaId, BigDecimal valorMinimo,
            BigDecimal valorMaximo, Pageable pageable) {

        buscarCategoria(categoriaId);

        return despesaRepository.findByCategoriaIdAndValorBetween(
                        categoriaId, valorMinimo, valorMaximo, pageable)
                .map(this::converterParaDTO);
    }

    public Page<DespesaResponseDTO> buscarPorCategoriaEPeriodo(
            Long categoriaId, LocalDate dataInicial,
            LocalDate dataFinal, Pageable pageable) {

        buscarCategoria(categoriaId);

        return despesaRepository.findByCategoriaIdAndDataBetween(
                        categoriaId, dataInicial, dataFinal, pageable)
                .map(this::converterParaDTO);
    }

    public BigDecimal somarDespesasPorPeriodo(LocalDate inicio, LocalDate fim) {
        return despesaRepository.somarDespesasPorPeriodo(inicio, fim);
    }

    private Despesa buscarEntidadePorId(Long id) {
        return despesaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Despesa não encontrada"));
    }

    private Categoria buscarCategoria(Long categoriaId) {
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "categoria não encontrada"));
    }

    private Usuario buscarUsuarioAutenticado(){

       String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "email não encontrado"));
    }

    private DespesaResponseDTO converterParaDTO(Despesa despesa) {

        CategoriaResponseDTO categoriaDTO = new CategoriaResponseDTO();
        categoriaDTO.setId(despesa.getCategoria().getId());
        categoriaDTO.setNome(despesa.getCategoria().getNome());

        return new DespesaResponseDTO(
                despesa.getId(),
                despesa.getDescricao(),
                despesa.getValor(),
                despesa.getData(),
                categoriaDTO
        );
    }
}
