package com.senac.cadastro.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveRetornarCódigo200() throws Exception {
        mockMvc.perform(get("/pessoas"))
        .andDo(print())
        .andExpect(status().isOk());
    }
}
