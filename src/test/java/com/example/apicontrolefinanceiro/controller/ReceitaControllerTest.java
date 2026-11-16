package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.repository.ReceitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ReceitaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ReceitaRepository receitaRepository;

    @BeforeEach
    void limparReceitas() {

        receitaRepository.deleteAll();
    }

    @Test
    void deveCadastrarReceita() throws Exception{

        mockMvc.perform(
                post("/receitas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":5000.00, \"data\":\"2026-11-16\"}")
        ).andExpect(status().isCreated());
    }
}
