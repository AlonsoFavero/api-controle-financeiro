
package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.dto.ResumoFinanceiroDTO;
import com.example.apicontrolefinanceiro.dto.TransacaoRequestDTO;
import com.example.apicontrolefinanceiro.dto.TransacaoResponseDTO;
import com.example.apicontrolefinanceiro.model.Categoria;
import com.example.apicontrolefinanceiro.model.TipoTransacao;
import com.example.apicontrolefinanceiro.model.Transacao;
import com.example.apicontrolefinanceiro.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Transações", description = "Gerenciamento de transações")
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    @Operation(summary = "Cadastra uma transação",
            description = "Registra uma nova transação com os dados informados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    public TransacaoResponseDTO salvar(@Valid @RequestBody TransacaoRequestDTO dados) {

        Transacao transacao = converterParaEntidade(dados);

        return new TransacaoResponseDTO(transacaoService.salvar(transacao));
    }

    @GetMapping
    @Operation(summary = "Lista as transações",
            description = "Retorna as transações cadastradas com paginação")
    @ApiResponse(responseCode = "200", description = "Transações listadas com sucesso")
    public Page<TransacaoResponseDTO> listar(Pageable pageable) {

        return transacaoService.listarPaginado(pageable)
                .map(transacao -> new TransacaoResponseDTO(transacao));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma transação por ID",
            description = "Retorna os dados de uma transação específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação encontrada"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    public TransacaoResponseDTO buscarPorId(@PathVariable Long id) {

        return transacaoService.buscarPorId(id)
                .map(transacao -> new TransacaoResponseDTO(transacao))
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Exclui uma transação",
            description = "Remove uma transação pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Transação excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada")
    })
    public void excluirPorId(@PathVariable Long id) {

        transacaoService.excluirPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma transação",
            description = "Atualiza os dados de uma transação existente pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Transação ou categoria não encontrada")
    })
    public TransacaoResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody TransacaoRequestDTO dados) {

        Transacao transacao = converterParaEntidade(dados);

        return new TransacaoResponseDTO(transacaoService.atualizar(id, transacao));
    }

    private Transacao converterParaEntidade(TransacaoRequestDTO dados) {

        Transacao transacao = new Transacao();

        transacao.setDescricao(dados.getDescricao());
        transacao.setValor(dados.getValor());
        transacao.setTipo(dados.getTipo());
        transacao.setData(dados.getData());
        Categoria categoria = transacaoService.buscarCategoriaPorId(dados.getCategoriaId());
        transacao.setCategoria(categoria);

        return transacao;
    }

    @GetMapping("/saldo")
    @Operation(summary = "Calcula o saldo",
            description = "Retorna o saldo financeiro considerando receitas e despesas")
    @ApiResponse(responseCode = "200", description = "Saldo calculado com sucesso")
    public BigDecimal calculaSaldo() {

        return transacaoService.calcularSaldo();
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Busca transações por tipo",
            description = "Retorna as transações filtradas pelo tipo informado")
    @ApiResponse(responseCode = "200", description = "Transações listadas com sucesso")
    public List<TransacaoResponseDTO> buscarTipo(@PathVariable TipoTransacao tipo) {

        return transacaoService.buscarPorTipo(tipo)
                .stream()
                .map(transacao -> new TransacaoResponseDTO(transacao))
                .toList();
    }

    @GetMapping("/receitas/total")
    @Operation(summary = "Calcula o total de receitas",
            description = "Retorna a soma dos valores de todas as receitas")
    @ApiResponse(responseCode = "200", description = "Total de receitas calculado com sucesso")
    public BigDecimal calcularReceitas() {

        return transacaoService.calcularTotalReceitas();
    }

    @GetMapping("/despesas/total")
    @Operation(summary = "Calcula o total de despesas",
            description = "Retorna a soma dos valores de todas as despesas")
    @ApiResponse(responseCode = "200", description = "Total de despesas calculado com sucesso")
    public BigDecimal calcularDespesas() {

        return transacaoService.calcularTotalDespesas();
    }

    @GetMapping("/periodo")
    @Operation(summary = "Filtra transações por período",
            description = "Retorna as transações registradas entre as datas inicial e final informadas")
    @ApiResponse(responseCode = "200", description = "Transações filtradas com sucesso")
    public List<TransacaoResponseDTO> buscarPeriodo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim) {

        return transacaoService.buscarPorPeriodo(inicio, fim)
                .stream()
                .map(transacao -> new TransacaoResponseDTO(transacao))
                .toList();
    }

    @GetMapping("/resumo")
    @Operation(summary = "Gera resumo financeiro por período",
            description = "Retorna o resumo financeiro das transações entre as datas informadas")
    @ApiResponse(responseCode = "200", description = "Resumo financeiro gerado com sucesso")
    public ResumoFinanceiroDTO resumirPorPeriodo(@RequestParam LocalDate inicio, LocalDate fim) {

        return transacaoService.calcularResumoPorPeriodo(inicio, fim);
    }

    @GetMapping("/ordenadas")
    @Operation(summary = "Lista transações por data",
            description = "Retorna as transações ordenadas da data mais recente para a mais antiga")
    @ApiResponse(responseCode = "200", description = "Transações listadas com sucesso")
    public List<TransacaoResponseDTO> listarPorData() {

        return transacaoService.listarPorDataDesc()
                .stream()
                .map(transacao -> new TransacaoResponseDTO(transacao))
                .toList();
    }

    @GetMapping("/valor")
    @Operation(summary = "Filtra transações por faixa de valor",
            description = "Retorna as transações com valores entre o mínimo e o máximo informados")
    @ApiResponse(responseCode = "200", description = "Transações filtradas com sucesso")
    public List<TransacaoResponseDTO> buscarPorValor(@RequestParam BigDecimal valorMinimo, @RequestParam BigDecimal valorMaximo) {

        return transacaoService.buscarPorFaixaDeValor(valorMinimo, valorMaximo)
                .stream()
                .map(transacao -> new TransacaoResponseDTO(transacao))
                .toList();
    }
}
