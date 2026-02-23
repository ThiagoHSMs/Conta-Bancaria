package com.financeiro.dto;

import java.math.BigDecimal;

public record TransacaoRequest (
    Long contaId,
    BigDecimal valor,
    String tipo    
){}
