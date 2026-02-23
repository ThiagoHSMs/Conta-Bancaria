package com.financeiro.dto;

import java.math.BigDecimal;

public record PagamentoConfirmadoEvent (
    Long transacaoId,
    Long contaId,
    BigDecimal valor
    
){} 

