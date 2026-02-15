package com.financeiro.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financeiro.model.Transacao;

public interface TransacaoRepository  extends JpaRepository<Transacao, UUID>{
    
}
