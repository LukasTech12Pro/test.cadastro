package com.senac.cadastro;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


@SpringBootTest
public class PessoaTests {

    @Autowired
    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testInserirNomeNaoPodeSerEmBrancoOuNulo() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("");  // Nome em branco
        pessoa.setEndereco("Rua X");
        pessoa.setEmail("teste@algumemail.com");
        pessoa.setTelefone("11 99999-8888");
        pessoa.setCpf("123.456.789-00");
        pessoa.setDataNascimento(LocalDate.of(2025, 5, 12));

        Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoa);
        assertFalse(violations.isEmpty(), "Deve haver erros de validação");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Nome é obrigatório")));
    }

    @Test
    public void testInserirEmailDeveSerValidoComPadrao() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João Silva");
        pessoa.setEndereco("Rua X");
        pessoa.setEmail("joao-email.com");  // Email definitivamente inválido
        pessoa.setTelefone("11 99999-8888");
        pessoa.setCpf("123.456.789-00");
        pessoa.setDataNascimento(LocalDate.of(1990, 5, 12));

        Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoa);
        assertFalse(violations.isEmpty(), "Deve haver erros de validação");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Email deve ser válido")));
    }

    @Test
    public void testInserirTelefoneDeveSerValidoComPadrao() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João Silva");
        pessoa.setEndereco("Rua X");
        pessoa.setEmail("joao@algumemail.com");
        pessoa.setTelefone("999998888");  // Telefone inválido
        pessoa.setCpf("123.456.789-00");
        pessoa.setDataNascimento(LocalDate.of(1990, 5, 12));

        Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoa);
        assertFalse(violations.isEmpty(), "Deve haver erros de validação");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Telefone deve estar no formato")));
    }

    @Test
    public void testInserirCPFDeveSerValidoComPadrao() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João Silva");
        pessoa.setEndereco("Rua X");
        pessoa.setEmail("joao@algumemail.com");
        pessoa.setTelefone("11 99999-8888");
        pessoa.setCpf("12345678900");  // CPF inválido (sem pontuação)
        pessoa.setDataNascimento(LocalDate.of(1990, 5, 12));

        Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoa);
        assertFalse(violations.isEmpty(), "Deve haver erros de validação");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("CPF deve estar no formato")));
    }

    @Test
    public void testInserirDataNascimentoDeveSerValida() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome("João Silva");
        pessoa.setEndereco("Rua X");
        pessoa.setEmail("joao@algumemail.com");
        pessoa.setTelefone("11 99999-8888");
        pessoa.setCpf("123.456.789-00");
        pessoa.setDataNascimento(LocalDate.of(2028, 5, 12));  // Data futura

        Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoa);
        assertFalse(violations.isEmpty(), "Deve haver erro de validação para data futura");
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Data de nascimento deve ser uma data no passado")));
    }

}