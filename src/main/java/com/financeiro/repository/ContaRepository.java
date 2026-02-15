package com.financeiro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financeiro.model.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long>{
    
}
