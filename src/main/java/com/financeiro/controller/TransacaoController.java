package com.financeiro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.dto.TransacaoRequest;
import com.financeiro.service.TransacaoService;


@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
    
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService){
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<String> criar(@RequestBody TransacaoRequest request){
        transacaoService.processarTransacao(request.contaId(), request.valor());
        return ResponseEntity.ok("Transacao processada com sucesso");
    }
        
}
