package com.example.apicontrolefinanceiro.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DespesaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void deveListarDespesas() throws Exception {

        mockMvc.perform(
                get("/despesas")
        ).andExpect(status().isOk());
    }

    @Test
    void deveCadastrarDespesa() throws Exception {

        mockMvc.perform(
                post("/despesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":150.50, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

        ).andExpect(status().isCreated());

    }
}
