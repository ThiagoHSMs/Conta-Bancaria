package com.financeiro.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation; // Importe este
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.exception.SaldoInsuficienteException;
import com.financeiro.model.Conta;
import com.financeiro.model.TipoTransacao;
import com.financeiro.model.Transacao;
import com.financeiro.repository.ContaRepository;
import com.financeiro.repository.TransacaoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ContaCorrenteService {

    private static final Logger log = LoggerFactory.getLogger(ContaCorrenteService.class);

    @Autowired
    private TransacaoRepository repository;

    @Autowired
    private ContaRepository contaRepository;
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(
        retryFor = { ObjectOptimisticLockingFailureException.class},
        maxAttempts= 12,
        backoff= @Backoff(delay = 100, multiplier= 2)
    )    
    public void processarPagamento(UUID idempotencyKey, Long contaId, BigDecimal valor){
    

        // 1. Verificação de Idempotência (Já processei esse ID antes?)
        if (repository.existsById(idempotencyKey)) {
            System.out.println("BOA! Idempotencia Funcionou");
            return; // Retorna com sucesso (Idempotente), mas não executa de novo.
        }

        // 2. Recupera a conta (Lock Otimista ou Pessimista)
        Conta conta = contaRepository.findById(contaId)
            .orElseThrow(() -> new EntityNotFoundException("Conta não encontrada"));

        // 3. Regra de Negócio
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente");
        }
    

        // 4. Execução
        conta.setSaldo(conta.getSaldo().subtract(valor));
        
        Transacao transacao = new Transacao(idempotencyKey, valor, TipoTransacao.DEBITO, LocalDateTime.now());
        
        // 5. Persistência
        contaRepository.save(conta);
        repository.save(transacao);
        
        // Se qualquer linha acima der erro, o Spring faz o ROLLBACK automático de tudo.
    }
}
