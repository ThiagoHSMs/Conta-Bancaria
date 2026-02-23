package com.financeiro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.financeiro.model.Conta;

import jakarta.persistence.LockModeType;


public interface ContaRepository extends JpaRepository<Conta, Long>{

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Conta> findById(Long id);
    
}
