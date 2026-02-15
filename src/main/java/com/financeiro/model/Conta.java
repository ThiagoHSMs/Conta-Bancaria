package com.financeiro.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Version;

@Entity
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal saldo;

    @Version
    private Integer version;

    public Conta(){}
    public Conta(BigDecimal saldo){ this.saldo = saldo; }

    public Long getId() { return id; }
    public BigDecimal getSaldo() { return saldo;}
    public void setSaldo(BigDecimal saldo) { this.saldo = saldo; }
    
}
