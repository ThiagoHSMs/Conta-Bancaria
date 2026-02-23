# 💳 Sistema Financeiro - Arquitetura Event-Driven com Spring Boot e Kafka

Projeto desenvolvido para simular um sistema bancário simplificado com foco em:

- Processamento transacional
- Arquitetura orientada a eventos
- Concorrência
- Desacoplamento entre serviços
- Containerização com Docker

---

## 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3
- Spring Data JPA
- Hibernate
- Apache Kafka
- Docker & Docker Compose
- PostgreSQL (ou H2, dependendo da sua config)
- Apache Benchmark (testes de carga)

---

## 🏗 Arquitetura

Fluxo principal:

Controller  
→ Service (@Transactional)  
→ Banco de Dados  
→ Publicação de Evento (Kafka)  
→ Consumers independentes  

Consumers implementados:

- 📄 Geração de comprovante em PDF
- 📲 Envio de notificação (push simulado)

Arquitetura orientada a eventos garante:

- Baixo acoplamento
- Escalabilidade
- Melhor separação de responsabilidades

---

## 📌 Funcionalidades

✔ Criar transação (débito/crédito)  
✔ Atualização de saldo  
✔ Persistência com controle transacional  
✔ Publicação de evento após commit  
✔ Geração automática de PDF  
✔ Notificação assíncrona  
✔ Suporte a múltiplas requisições simultâneas  

---

## 📊 Teste de Carga

Teste realizado com Apache Benchmark:

- 50 requisições
- Concurrency Level: 10
- 0 falhas
- Processamento estável
- Eventos publicados corretamente
- PDFs gerados de forma assíncrona

---

## 🔐 Modelagem de Domínio

### Transacao

- id
- conta
- valor
- tipo (ENUM: DEBITO / CREDITO)
- data

Uso de `EnumType.STRING` para garantir segurança de tipo no banco.

---

## 🐳 Executando com Docker

```bash
docker compose up --build
