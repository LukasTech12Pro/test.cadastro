package com.senac.cadastro.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.senac.cadastro.model.Pessoa;
import com.senac.cadastro.service.PessoaService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PessoaControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private PessoaController pessoaController;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveRetornarCódigo200() throws Exception {
        mockMvc.perform(get("/pessoas"))
        .andDo(print())
        .andExpect(status().isOk());
    }

    @Test
    public void listarPessoas_ReturnListOfPessoas_WhenPessoasExist() {
        Pessoa pessoa1 = new Pessoa(
                1L, "João Silva", "Rua A", "joao@email.com", "11 99999-9999",
                "123.456.789-00", LocalDate.of(1990, 5, 20));
        
        Pessoa pessoa2 = new Pessoa(
                2L, "Maria Souza", "Rua B", "maria@email.com", "11 98888-8888",
                "987.654.321-00", LocalDate.of(1995, 8, 15));

        List<Pessoa> pessoasEsperadas = Arrays.asList(pessoa1, pessoa2);
        Mockito.when(pessoaService.listarPessoas()).thenReturn(pessoasEsperadas);

        ResponseEntity<List<Pessoa>> response = pessoaController.listarPessoas();
        
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("João Silva", response.getBody().get(0).getNome());
        assertEquals("Maria Souza",response.getBody().get(1).getNome());
    }

    @Test
    public void testListarPessoasPelaLetra() {
        String prefixo = "A";
        Pessoa pessoa = new Pessoa(1L, "Ana", "Rua A", "ana@email.com", "11 91234-5678", "123.456.789-00", LocalDate.of(2000, 1, 1));
        when(pessoaService.listarPessoasPelaLetra(prefixo)).thenReturn(Arrays.asList(pessoa));


        ResponseEntity<List<Pessoa>> response = pessoaController.listarPessoasPelaLetra(prefixo);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Ana", response.getBody().get(0).getNome());
        verify(pessoaService, times(1)).listarPessoasPelaLetra(prefixo);
    }

    @Test
    public void testCadastrarUsuarios_ComDadosValidos() {
        Pessoa pessoa = new Pessoa(1L, "Carlos", "Rua B", "carlos@email.com", "11 92345-6789", "987.654.321-00", LocalDate.of(1995, 5, 10));
        when(bindingResult.hasErrors()).thenReturn(false);
        when(pessoaService.cadastrarPessoas(pessoa)).thenReturn(pessoa);

        ResponseEntity<Pessoa> response = pessoaController.cadastrarUsuarios(pessoa, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Carlos", response.getBody().getNome());
        verify(pessoaService, times(1)).cadastrarPessoas(pessoa);
    }

    @Test
    public void testCadastrarUsuarios_ComErrosDeValidacao() {
        Pessoa pessoa = new Pessoa();
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(
            Arrays.asList(new ObjectError("nome", "nome é obrigatório"))
        );

        ResponseEntity<Pessoa> response = pessoaController.cadastrarUsuarios(pessoa, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        verify(pessoaService, never()).cadastrarPessoas(any());
    }
}