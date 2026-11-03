package com.example.apicontrolefinanceiro.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void deveListarCategorias() throws Exception{

        mockMvc.perform(
                get("/categorias")
        ).andExpect(status().isOk());
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
}
