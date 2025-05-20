package com.senac.cadastro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senac.cadastro.model.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    
    List<Pessoa> findByNomeStartingWithIgnoreCase(String prefixo);
}
