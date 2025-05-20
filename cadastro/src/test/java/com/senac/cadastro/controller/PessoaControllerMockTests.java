package com.senac.cadastro.controller;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.senac.cadastro.model.Pessoa;
import com.senac.cadastro.service.PessoaService;


@WebMvcTest(PessoaController.class)
public class PessoaControllerMockTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    PessoaService pessoaService;

    @Test
    public void deveRetornarCodigo200() throws Exception {
        when(pessoaService.listarPessoas()).thenReturn(new ArrayList<Pessoa>());
        mockMvc.perform(get("/pessoas"))
        .andDo(print())
        .andExpect(status().isOk());
    }
}

