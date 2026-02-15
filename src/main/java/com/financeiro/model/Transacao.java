package com.financeiro.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;


@Entity
public class Transacao {
    
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private TipoTransacao tipo;

    private BigDecimal valor;
    private LocalDateTime dataHora;

    // construtor padrão exigido pelo JPA
    public Transacao(){

    }

    public Transacao(UUID id, BigDecimal valor, TipoTransacao tipo, LocalDateTime dataHora){
     this.id = id;
     this.valor = valor;
     this.tipo = tipo;
     this.dataHora = dataHora;   
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public TipoTransacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransacao tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    
    
}
