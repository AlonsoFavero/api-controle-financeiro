package com.example.apicontrolefinanceiro.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DespesaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void configurarCategoria() throws Exception{

        mockMvc.perform(
                post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Alimentação\"}")
        ).andExpect(status().isCreated());
    }

    @Test
    void deveListarDespesa() throws Exception {

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

    @Test
    void deveBuscarDespesaPorId() throws Exception{

       MvcResult resultado = mockMvc.perform(
                post("/despesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":150.50, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

        ).andExpect(status().isCreated())
               .andReturn();

        String resposta = resultado.getResponse().getContentAsString();
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                get("/despesas/{id}", id)
        ).andExpect(status().isOk());
    }

    @Test
    void deveAtualizarDespesa() throws Exception{

        MvcResult resultado = mockMvc.perform(
                        post("/despesas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"descricao\":\"Conta de energia\", \"valor\":150.50, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

                ).andExpect(status().isCreated())
                .andReturn();

        String resposta = resultado.getResponse().getContentAsString();
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                put("/despesas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia atualizada\", \"valor\":180.50, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")
        ).andExpect(status().isOk());
    }

    @Test
    void deveExcluirDespesa() throws Exception{

        MvcResult resultado = mockMvc.perform(
                        post("/despesas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"descricao\":\"Conta de energia\", \"valor\":150.50, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

                ).andExpect(status().isCreated())
                .andReturn();

        String resposta = resultado.getResponse().getContentAsString();
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                delete("/despesas/{id}", id)
        ).andExpect(status().isNoContent());
    }

    @Test
    void deveRejeitarDespesaSemDescricao() throws Exception{

        mockMvc.perform(
                post("/despesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"\", \"valor\":150.50, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.erro").value("Erro de validação"))
                .andExpect(jsonPath("$.mensagem").value("must not be blank"));;
    }

    @Test
    void deveRejeitarDespesaComValorNegativo() throws Exception{

        mockMvc.perform(
                post("/despesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"conta de energia\", \"valor\": -150.50, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.erro").value("Erro de validação"))
                .andExpect(jsonPath("$.mensagem").value("must be greater than 0"));;
    }

    @Test
    void deveRejeitarDespesaSemData() throws Exception{

        mockMvc.perform(
                post("/despesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"conta de energia\", \"valor\":150.50, \"data\":null, \"categoria\":{\"id\":1}}")

        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.erro").value("Erro de validação"))
                .andExpect(jsonPath("$.mensagem").value("must not be null"));;
    }

    @Test
    void deveRejeitarDespesaSemCategoria() throws Exception{

        mockMvc.perform(
                post("/despesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"conta de energia\", \"valor\":150.50, \"data\":\"2026-11-07\", \"categoria\":null}")

        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.erro").value("Erro de validação"))
                .andExpect(jsonPath("$.mensagem").value("must not be null"));;
    }

    @Test
    void deveRejeitarDespesaSemValor() throws Exception{
        mockMvc.perform(
                post("/despesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"conta de energia\", \"valor\":null, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveRejeitarDespesaComValorZero() throws Exception{

        mockMvc.perform(
                post("/despesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"conta de energia\", \"valor\":0, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveRejeitarAtualizacaoComValorNegativo() throws Exception{

      MvcResult resultado =  mockMvc.perform(
                post("/despesas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":150.50, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

        ).andExpect(status().isCreated())
              .andReturn();

        String resposta = resultado.getResponse().getContentAsString();
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                put("/despesas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":-100, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.erro").value("Erro de validação"))
                .andExpect(jsonPath("$.mensagem").value("must be greater than 0"));;
    }

    @Test
    void deveRejeitarAtualizacaoSemDescricao() throws Exception{

        MvcResult resultado =  mockMvc.perform(
                        post("/despesas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"descricao\":\"Conta de energia\", \"valor\":150.50, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

                ).andExpect(status().isCreated())
                .andReturn();

        String resposta = resultado.getResponse().getContentAsString();
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                put("/despesas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"\", \"valor\":150.50, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.erro").value("Erro de validação"))
                .andExpect(jsonPath("$.mensagem").value("must not be blank"));;
    }

    @Test
    void deveRejeitarAtualizacaoSemCategoria() throws Exception{

        MvcResult resultado =  mockMvc.perform(
                        post("/despesas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"descricao\":\"Conta de energia\", \"valor\":150.50, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

                ).andExpect(status().isCreated())
                .andReturn();

        String resposta = resultado.getResponse().getContentAsString();
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                put("/despesas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":150.50, \"data\":\"2026-11-07\", \"categoria\": null}")

        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.erro").value("Erro de validação"))
                .andExpect(jsonPath("$.mensagem").value("must not be null"));
    }

    @Test
    void deveRejeitarAtualizacaoSemData() throws Exception{

        MvcResult resultado =  mockMvc.perform(
                        post("/despesas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"descricao\":\"Conta de energia\", \"valor\":150.50, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

                ).andExpect(status().isCreated())
                .andReturn();

        String resposta = resultado.getResponse().getContentAsString();
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                put("/despesas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":150.50, \"data\": null, \"categoria\":{\"id\":1}}")

        ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.erro").value("Erro de validação"))
                .andExpect(jsonPath("$.mensagem").value("must not be null"));;
    }

    @Test
    void deveRejeitarAtualizacaoSemValor() throws Exception{

        MvcResult resultado =  mockMvc.perform(
                        post("/despesas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"descricao\":\"Conta de energia\", \"valor\":150.50, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

                ).andExpect(status().isCreated())
                .andReturn();

        String resposta = resultado.getResponse().getContentAsString();
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                put("/despesas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":null, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveRejeitarAtualizacaoComValorZero() throws Exception{

        MvcResult resultado =  mockMvc.perform(
                        post("/despesas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"descricao\":\"Conta de energia\", \"valor\":150.50, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

                ).andExpect(status().isCreated())
                .andReturn();

        String resposta = resultado.getResponse().getContentAsString();
        Number idNumero = JsonPath.read(resposta, "$.id");
        Long id = idNumero.longValue();

        mockMvc.perform(
                put("/despesas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"descricao\":\"Conta de energia\", \"valor\":0, \"data\":\"2026-11-07\", \"categoria\":{\"id\":1}}")

        ).andExpect(status().isBadRequest());
    }

    @Test
    void deveRetornar404QuandoDespesaNaoExistir() throws Exception{

        mockMvc.perform(
                get("/despesas/999")
        ).andExpect(status().isNotFound());
    }
}
