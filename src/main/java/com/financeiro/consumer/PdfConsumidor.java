package com.financeiro.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.financeiro.dto.PagamentoConfirmadoEvent;
import com.financeiro.service.PdfService;

@Component
public class PdfConsumidor {

    private final PdfService pdfService;

    public PdfConsumidor(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @KafkaListener(topics = "topico-transacoes", groupId= "grupo-notificacao")
    public void enviarpush(PagamentoConfirmadoEvent evento) {
        System.out.println("[PUSH] Enviando notificacao para conta " + evento.contaId());
    }

    @KafkaListener(topics = "topico-transacoes", groupId= "grupo-pdf")
    public void gerarComprovante(PagamentoConfirmadoEvent evento){
        System.out.println("[PDF] Gerando comprovante da transacao: " + evento.transacaoId());
        
        pdfService.gerarComprovante(evento.transacaoId(), evento.contaId(), evento.valor());

    }
}
