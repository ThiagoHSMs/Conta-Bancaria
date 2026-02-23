package com.financeiro.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.financeiro.model.Transacao;

public interface TransacaoRepository  extends JpaRepository<Transacao, Long>{
    
}
