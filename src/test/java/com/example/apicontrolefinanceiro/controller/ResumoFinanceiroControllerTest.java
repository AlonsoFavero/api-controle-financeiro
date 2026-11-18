package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.repository.DespesaRepository;
import com.example.apicontrolefinanceiro.repository.ReceitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @BeforeEach
    void limparRespositories() {

        despesaRepository.deleteAll();
        receitaRepository.deleteAll();
    }

    @Test
    void deveRetornarResumoFinanceiro() throws Exception{

        mockMvc.perform(
                get("/resumo")
                        .param("inicio", "2026-10-01")
                        .param("fim", "2026-10-31")
        ).andExpect(status().isOk());
    }

}
