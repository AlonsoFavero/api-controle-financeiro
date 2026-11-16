package com.example.apicontrolefinanceiro.controller;

import com.example.apicontrolefinanceiro.repository.ReceitaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;


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
}
