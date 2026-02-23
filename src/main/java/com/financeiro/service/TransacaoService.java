package com.financeiro.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.financeiro.dto.PagamentoConfirmadoEvent;
import com.financeiro.model.Conta;
import com.financeiro.model.TipoTransacao;
import com.financeiro.model.Transacao;
import com.financeiro.repository.ContaRepository;
import com.financeiro.repository.TransacaoRepository;

@Service
public class TransacaoService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public TransacaoService(
            ContaRepository contaRepository,
            TransacaoRepository transacaoRepository,
            KafkaTemplate<String, Object> kafkaTemplate) {
        this.contaRepository = contaRepository;
        this.transacaoRepository = transacaoRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional("transactionManager")
    @Retryable(retryFor = OptimisticLockingFailureException.class)
    public void processarTransacao(Long contaId, BigDecimal valor) {

        Conta conta = contaRepository.findById(contaId)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));

        conta.setSaldo(conta.getSaldo().subtract(valor));
        contaRepository.save(conta);

        Transacao transacao = new Transacao();
        transacao.setConta(conta);
        transacao.setValor(valor);
        transacao.setData(LocalDateTime.now());
        transacao.setTipo(TipoTransacao.DEBITO);

        transacaoRepository.save(transacao);

        PagamentoConfirmadoEvent evento =
                new PagamentoConfirmadoEvent(
                        transacao.getId(),
                        conta.getId(),
                        valor
                );

        kafkaTemplate.send("topico-transacoes", evento);
    }
}