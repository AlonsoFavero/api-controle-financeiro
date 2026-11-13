package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.repository.DespesaRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    DespesaRepository despesaRepository;

    @Test
    void deveListarCategorias() throws Exception{

        mockMvc.perform(
                post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                \"nome\":\"Alimentação\"
                                }
                                """)
        ).andExpect(status().isCreated());

        mockMvc.perform(
                get("/categorias")
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.nome == 'Alimentação')]").exists());
    }

    @Test
    void deveCadastrarCategoria() throws Exception{

        mockMvc.perform(
                post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                \"nome\":\"Alimentação\"
                                }
                                """)
        ).andExpect(status().isCreated());
    }

    @Test
    void deveRejeitarCategoriaSemNome() throws Exception{

        mockMvc.perform(
                post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                \"nome\":\"\"
                                }
                                """)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornarDadosDaCategoria() throws Exception{

        mockMvc.perform(
                post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                \"nome\":\"Transporte\"
                                }
                                """)
        ).andExpect(status().isCreated())
                .andExpect(content().json("""
             {"nome":"Transporte"}
        """));
    }

    @Test
    void deveBuscarCategoriaPorId() throws Exception{

      MvcResult resultado = mockMvc.perform(
                post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                \"nome\":\"Lazer\"
                                }
                                """)
        ).andExpect(status().isCreated())
                .andReturn()  ;

        String resposta = resultado.getResponse().getContentAsString();
        System.out.println(resposta);
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                        get("/categorias/{id}", id)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Lazer"));
    }

    @Test
    void deveAtualizarCategoria() throws Exception{

        MvcResult resultado = mockMvc.perform(
                        post("/categorias")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                \"nome\":\"Lazer\"
                                }
                                """)
                ).andExpect(status().isCreated())
                .andReturn()  ;

        String resposta = resultado.getResponse().getContentAsString();
        System.out.println(resposta);
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                        put("/categorias/{id}", id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                \"nome\":\"Entretenimento\"
                                }
                                """
                                )
        ).andExpect(status().isOk())
        .andExpect(jsonPath("$.nome").value("Entretenimento"));
    }

    @Test
    void deveExcluirCategoria() throws Exception {


        MvcResult resultado = mockMvc.perform(
                        post("/categorias")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                        {
                                        \"nome\":\"Lazer\"
                                        }
                                        """)
                ).andExpect(status().isCreated())
                .andReturn();

        String resposta = resultado.getResponse().getContentAsString();
        System.out.println(resposta);
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                delete("/categorias/{id}", id)
        ).andExpect(status().isNoContent());
    }

    @Test
    void deveImpedirExclusaoDeCategoriaComDespesasVinculadas() throws Exception{

        MvcResult resultado = mockMvc.perform(
                        post("/categorias")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                                {
                                \"nome\":\"Alimentação\"
                                }
                                """)
                ).andExpect(status().isCreated())
                .andReturn()  ;

        String resposta = resultado.getResponse().getContentAsString();
        System.out.println(resposta);
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                post("/despesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                              { 
                              "descricao": "Conta de energia",
                               "valor": 150.50,
                               "data": "2026-11-07",
                                "categoria": {"id": %d}
                               }
                               """.formatted(id))
        ).andExpect(status().isCreated());

        System.out.println(despesaRepository.findByCategoriaId(id));
        assertFalse(despesaRepository.findByCategoriaId(id).isEmpty());

        mockMvc.perform(
                delete("/categorias/{id}", id)
        ).andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensagem").value("categoria possui despesas vinculadas"));
    }
}
