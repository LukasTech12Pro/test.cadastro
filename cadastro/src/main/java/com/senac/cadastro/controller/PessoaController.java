package com.senac.cadastro.controller;

import java.util.List;

import org.springframework.http.HttpStatus; // Importando HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senac.cadastro.model.Pessoa;
import com.senac.cadastro.service.PessoaService;

import jakarta.validation.Valid; // Importando List

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    // Método GET para listar todas as pessoas
    @GetMapping
    public ResponseEntity<List<Pessoa>> listarPessoas() {
        List<Pessoa> pessoas = pessoaService.listarPessoas();
        return ResponseEntity.ok(pessoas);
    }

    //Método GET para listar todos as pessoas pela letra
    @GetMapping("/letra/{prefixo}")
    public ResponseEntity<List<Pessoa>> listarPessoasPelaLetra(@PathVariable String prefixo) {
        List<Pessoa> pessoas = pessoaService.listarPessoasPelaLetra(prefixo);
        return ResponseEntity.ok(pessoas);
    }

    @PostMapping
    public ResponseEntity<Pessoa> cadastrarUsuarios(@RequestBody @Valid Pessoa pessoa, BindingResult result) {
        if(result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            result.getAllErrors().forEach(error -> sb.append(error.getDefaultMessage()).append("\n"));
            return ResponseEntity.badRequest().body(null);
        }

        Pessoa pessoaSalva = pessoaService.cadastrarPessoas(pessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
    }
    
}