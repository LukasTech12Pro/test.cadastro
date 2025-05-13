package com.senac.cadastro;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class PessoaService {

    private PessoaRepository pessoaRepository;

    public PessoaService() {
    }

    public Pessoa salvarPessoa(@Valid Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }
}
