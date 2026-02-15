
🏦 Sistema de Simulação Bancária - Multithreading & Integridade
Este projeto vai além de um simples CRUD bancário. Ele é um laboratório de testes para cenários de alta concorrência e consistência de dados em sistemas financeiros, utilizando Spring Boot e Java moderno.

🎯 Objetivo do Projeto
Validar a segurança de transações financeiras quando múltiplos processos tentam debitar valores de uma mesma conta simultaneamente. O projeto foca em impedir problemas como o "Lost Update" (quando uma transação sobrescreve a outra) e garantir a precisão centesimal do saldo.

🛠️ Stack Tecnológica
Java 17

Spring Boot 3

Spring Data JPA: Com foco em controle de concorrência.

H2 Database: Banco de dados em memória para testes rápidos.

Concurrent API: Uso de ExecutorService, CountDownLatch e Threads.

🧠 Atitude de Aprendizado: "Deep Dive"
O grande diferencial deste projeto não é apenas o código final, mas o processo de desenvolvimento. Durante a construção, adotei uma postura ativa de investigação técnica, buscando entender cada "engrenagem" do sistema:

Gestão de Threads: Em vez de apenas rodar o código, busquei entender por que o ExecutorService é superior à criação manual de threads e como o pool de threads otimiza recursos.

Precisão Matemática: Questionei o uso de tipos primitivos e implementei BigDecimal, dominando métodos como multiply e subtract para garantir que nenhum centavo fosse perdido.

Resolução de Conflitos JPA: Enfrentei e resolvi erros de anotações (como o conflito entre @Version do Hibernate vs. JPA), entendendo a importância dos imports corretos para o funcionamento do Optimistic Locking.

Logs Estratégicos: Implementei o uso de System.err para isolar falhas de fluxo, facilitando o debug em ambientes multithread.

🚀 Características Técnicas Destacadas
⚡ Teste de Estresse Simultâneo
O projeto utiliza um CountDownLatch para garantir que todas as threads iniciem o processamento exatamente ao mesmo tempo, simulando um cenário real de pico de acessos.

🛡️ Idempotência e Segurança
Cada tentativa de pagamento gera um UUID único. Isso demonstra a preocupação com a idempotência: garantir que uma operação não seja executada duas vezes por erro de rede ou repetição de comando.

📊 Validação de Saldo Final
Ao final de cada execução, o sistema calcula matematicamente o saldo esperado (saldoInicial - (valorTransacao * numThreads)) e compara com o saldo real no banco de dados, validando a integridade da lógica.

📝 Como Executar
Clone o repositório.

Certifique-se de ter o Java 17 instalado.

Execute a classe MinhaContaApplication.

Acompanhe no console o relatório detalhado de tempo de execução e validação de saldo.
