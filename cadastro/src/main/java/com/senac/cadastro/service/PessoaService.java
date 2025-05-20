package com.senac.cadastro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.senac.cadastro.model.Pessoa;
import com.senac.cadastro.repository.PessoaRepository;

import jakarta.validation.Valid;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public Pessoa salvarPessoa(@Valid Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    
    //Listar todas as pessoas 
    public List<Pessoa> listarPessoas() {
        return pessoaRepository.findAll();
    }

    public List<Pessoa> listarPessoasPelaLetra(String prefixo) {
        return pessoaRepository.findByNomeStartingWithIgnoreCase(prefixo);
    }

    //Cadastrar Pessoas
    public Pessoa cadastrarPessoas(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }
}