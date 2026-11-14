package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.model.Despesa;
import com.example.apicontrolefinanceiro.service.DespesaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/despesas")
public class DespesaController {

    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService){

        this.despesaService = despesaService;
    }

    @GetMapping
    public Page<Despesa> listar(Pageable pageable){

        return despesaService.listar(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Despesa salvar(@RequestBody @Valid Despesa despesa){

        return despesaService.salvar(despesa);
    }

    @GetMapping("/{id}")
    public Despesa buscarPorId(@PathVariable Long id){

        return despesaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Despesa atualizar(@PathVariable Long id, @RequestBody @Valid Despesa dados){

        return despesaService.atualizar(id, dados);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id){

         despesaService.excluir(id);
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<Despesa> listarPorCategoria(@PathVariable Long categoriaId){

        return despesaService.buscarPorCategoria(categoriaId);
    }

    @GetMapping("/periodo")
    public List<Despesa> listarPorPeriodo(@RequestParam LocalDate inicio, @RequestParam LocalDate fim){

        if(inicio.isAfter(fim)){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data inicial não pode ser posterior a data final");
        }

        return despesaService.buscarPorPeriodo(inicio, fim);
    }

    @GetMapping("/valor")
    public List<Despesa> listarPorFaixaDeValor(@RequestParam BigDecimal valorMinimo,@RequestParam BigDecimal valorMaximo){

        if(valorMinimo.compareTo(valorMaximo) > 0){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "valor minimo não pode ser maior que o valor maximo");

        }

        return despesaService.buscarPorFaixaDeValor(valorMinimo, valorMaximo);
    }

    @GetMapping("/categoria-valor")
    public List<Despesa> buscarPorCategoriaEFaixaDeValor(@RequestParam Long categoriaId, @RequestParam BigDecimal valorMinimo, @RequestParam BigDecimal valorMaximo){

        if(valorMinimo.compareTo(valorMaximo) > 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"valor minimo não pode ser maior que o valor maximo"
            );
        }

        return despesaService.buscarPorCategoriaEFaixaDeValor(categoriaId,valorMinimo,valorMaximo);
    }

    @GetMapping("/categoria-periodo")
    public List<Despesa> buscarPorCategoriaEPeriodo(@RequestParam Long categoriaId,@RequestParam LocalDate dataInicial,@RequestParam LocalDate dataFinal){

        if(dataInicial.isAfter(dataFinal)){

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "data inicial não pode ser posterior a data final");
        }

        return despesaService.buscarPorCategoriaEPeriodo(categoriaId, dataInicial, dataFinal);
    }
}
