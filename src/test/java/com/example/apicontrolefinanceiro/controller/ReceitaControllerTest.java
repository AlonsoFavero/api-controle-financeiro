package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.repository.ReceitaRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.MvcResult;


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

    @Test
    void deveListarReceitas() throws Exception{

       mockMvc.perform(
               get("/receitas")
       ).andExpect(status().isOk());
    }

    @Test
    void deveBuscarReceitaPorId() throws Exception{

       MvcResult resultado = mockMvc.perform(
                post("/receitas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":5000.00, \"data\":\"2026-11-16\"}")
        ).andExpect(status().isCreated())
               .andReturn();


        String resposta = resultado.getResponse().getContentAsString();
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                get("/receitas/{id}", id)
        ).andExpect(status().isOk());
    }

    @Test
    void deveAtualizarReceita() throws Exception{

        MvcResult resultado = mockMvc.perform(
                        post("/receitas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"descricao\":\"Conta de energia\", \"valor\":5000.00, \"data\":\"2026-11-16\"}")
                ).andExpect(status().isCreated())
                .andReturn();


        String resposta = resultado.getResponse().getContentAsString();
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                put("/receitas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia Atualizada\", \"valor\":4000.00, \"data\":\"2026-11-17\"}")
        ).andExpect(status().isOk());
    }

    @Test
    void deveExcluirReceita() throws Exception{

        MvcResult resultado = mockMvc.perform(
                        post("/receitas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"descricao\":\"Conta de energia\", \"valor\":5000.00, \"data\":\"2026-11-16\"}")
                ).andExpect(status().isCreated())
                .andReturn();


        String resposta = resultado.getResponse().getContentAsString();
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                delete("/receitas/{id}", id)
        ).andExpect(status().isNoContent());
    }

    @Test
    void deveRejeitarReceitaComDescricaoVazia() throws Exception{

        mockMvc.perform(
                post("/receitas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"  \", \"valor\":5000.00, \"data\":\"2026-11-16\"}")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveRejeitarReceitaComValorNegativo() throws Exception{

        mockMvc.perform(
                post("/receitas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":-5000.00, \"data\":\"2026-11-16\"}")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveRejeitarComValorZero() throws Exception{

        mockMvc.perform(
                post("/receitas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":0, \"data\":\"2026-11-16\"}")
        ).andExpect(status().isBadRequest());
    }
}
