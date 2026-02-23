package com.financeiro;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;

import com.financeiro.model.Conta;
import com.financeiro.repository.ContaRepository;


@SpringBootApplication
@EnableRetry
public class MinhaContaApplication {
    
    public static void main(String[] args){
        SpringApplication.run(MinhaContaApplication.class, args);
    }

        @Bean
    CommandLineRunner init(ContaRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Conta conta = new Conta();
                conta.setSaldo(new BigDecimal("50000.00"));
                repository.save(conta);
                System.out.println("Conta criada com saldo 50000");
            }
        };
    }
}

    /*@Bean
    public CommandLineRunner demo(ContaCorrenteService service, ContaRepository contaRepo){
        return (args) -> {
            // 1- Setup inicial
            BigDecimal saldoInicial = new BigDecimal("50000.00");
            Conta minhaConta = contaRepo.save(new Conta(saldoInicial));
            Long id = minhaConta.getId();

            System.out.println("\n=== INICIANDO TESTE DE ESTRESSE COM RETRY ===");

            // 2- Configuração do Stress Test
            int numeroDeThreads = 10;
            BigDecimal valorPorTransacao = new BigDecimal("100.00");
            ExecutorService executor = Executors.newFixedThreadPool(numeroDeThreads);

            System.out.println("Disparando " + numeroDeThreads + " pagamento de R$ " + valorPorTransacao + " simultaneamente ...");

            for ( int i = 0; i < numeroDeThreads; i++) {
                executor.execute(() -> {
                    try {
                        // Cada thread tenta usar um UUID único para passar pela idempotência
                        service.processarPagamento(UUID.randomUUID(), id, valorPorTransacao);
                    } catch (Exception e) {
                        System.err.println("Thread falhou (mesmo com retry): " + e.getMessage());
                    }
                });
            }

            // 3- Aguardar Finalização das threads
            executor.shutdown();
            if (executor.awaitTermination(10, TimeUnit.SECONDS)){
                System.out.println("--- Todas as threads foram processadas ---");
            }
            // 4- Verificação matemática da prova Real
            System.out.println("\n---------------------------------------------------");
            Conta contaFinal = contaRepo.findById(id).orElseThrow();
           
            // Cálculo esperado: Saldo Inicial - (Threads * Valor)
            BigDecimal totalDebitado = valorPorTransacao.multiply(new BigDecimal(numeroDeThreads));
            BigDecimal saldoEsperado = saldoInicial.subtract(totalDebitado);

            System.out.println("SALDO INICIAL: R$ " + saldoInicial);
            System.out.println("TOTAL DEBITADO: R$ " + totalDebitado);
            System.out.println("SALDO EM BANCO: R$ " + contaFinal.getSaldo());
            System.out.println("SALDO ESPERADO: R$ " + saldoEsperado);

            if (contaFinal.getSaldo().compareTo(saldoEsperado) == 0){
                System.out.println("\nResultado: SUCESSO ABSOLUTO! ");
                System.out.println("O @Retryable resolveu os conflitos de concorrência");
            } else{
                System.out.println("\nRESULTADO: FALHA DE INTEGRIDADE!");
                System.out.println("O saldo não bate com o esperado.");
            }
            System.out.println("---------------------------------------------------\n");
        };
    } */


