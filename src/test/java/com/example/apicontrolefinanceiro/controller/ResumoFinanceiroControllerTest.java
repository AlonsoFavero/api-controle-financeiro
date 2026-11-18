package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.model.Categoria;
import com.example.apicontrolefinanceiro.repository.CategoriaRepository;
import com.example.apicontrolefinanceiro.repository.DespesaRepository;
import com.example.apicontrolefinanceiro.repository.ReceitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ResumoFinanceiroControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DespesaRepository despesaRepository;

    @Autowired
    ReceitaRepository receitaRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @BeforeEach
    void limparRespositories() {

        despesaRepository.deleteAll();
        receitaRepository.deleteAll();
    }

    @Test
    void deveRetornarResumoFinanceiro() throws Exception{

        Categoria categoria = new Categoria();
        categoria.setNome("Contas");
        Categoria categoriaSalva = categoriaRepository.save(categoria);

        mockMvc.perform(
                post("/receitas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":3000.00, \"data\":\"2026-10-16\"}")
        ).andExpect(status().isCreated());

        mockMvc.perform(
                post("/receitas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":2000.00, \"data\":\"2026-10-16\"}")
        ).andExpect(status().isCreated());

        mockMvc.perform(
                post("/despesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":1500.00, \"data\":\"2026-10-16\", \"categoria\":{\"id\":" + categoriaSalva.getId() + "}}")
        ).andExpect(status().isCreated());

        mockMvc.perform(
                get("/resumo")
                        .param("inicio", "2026-10-01")
                        .param("fim", "2026-10-31")
        ).andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalReceitas").value(5000.00))
                .andExpect(jsonPath("$.totalDespesas").value(1500.00))
                .andExpect(jsonPath("$.saldo").value(3500.00));;
    }

}
