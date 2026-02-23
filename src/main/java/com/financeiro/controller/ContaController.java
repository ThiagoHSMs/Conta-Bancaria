package com.financeiro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.financeiro.model.Conta;
import com.financeiro.repository.ContaRepository;


@RestController
@RequestMapping("/contas")

public class ContaController {
    
    private ContaRepository contaRepository;

    @GetMapping("/{id}")
    public Conta buscar(@PathVariable Long id) {
        return contaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conta nao Encontrada"));
    }
    
}
