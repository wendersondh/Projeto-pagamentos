# Serviço de Pagamentos

Projeto acadêmico (IFPB) que integra uma aplicação Spring Boot com o gateway de pagamentos [Asaas](https://www.asaas.com/). Permite cadastrar clientes e pedidos, gerar cobranças (PIX, boleto, cartão), gerenciar chaves Pix e QR Codes, e receber notificações assíncronas de mudança de status de pagamento via webhook.

## O que o projeto faz

- Cadastra clientes e sincroniza cada um com um cliente correspondente no Asaas.
- Cadastra pedidos vinculados a um cliente.
- Gera cobranças (pagamentos) para um pedido junto ao Asaas.
- Cria chaves Pix e QR Codes estáticos.
- Recebe as notificações que o Asaas envia quando o status de um pagamento muda (confirmado, vencido, estornado) e atualiza o registro local — esse processamento é feito de forma assíncrona via Kafka (ver seção abaixo).

## Tecnologias

- **Java 21**
- **Spring Boot 3.5.4** (Web, Data JPA, Validation)
- **PostgreSQL** — persistência de Cliente, Pedido e Pagamento
- **Apache Kafka** (via `spring-kafka`) — desacopla o recebimento do webhook do Asaas do seu processamento
- **springdoc-openapi / Swagger UI** — documentação interativa da API
- **Lombok**
- **Maven** (com wrapper `mvnw`)
- **Docker / Docker Compose** — para subir o Kafka localmente

## Como rodar

1. Suba o Kafka local:
   ```bash
   docker compose up -d kafka
   ```
2. Tenha um PostgreSQL disponível e configure as variáveis de ambiente:
   - `BANCO` — nome do banco
   - `USERNAME` — usuário do banco
   - `PASSWORD` — senha do banco
   - `API_KEY` — chave de API do Asaas (sandbox)
3. Rode a aplicação (pela IDE ou `./mvnw spring-boot:run`).
4. Acesse a documentação interativa em `http://localhost:8080/swagger-ui/index.html`.

## Endpoints

### Clientes — `/api/clientes`
Cadastro e consulta de clientes, sincronizados com o Asaas.

| Método | Rota | Resumo |
|---|---|---|
| POST | `/api/clientes` | Cadastra um novo cliente (cria localmente e no Asaas) |
| GET | `/api/clientes` | Lista todos os clientes cadastrados |
| GET | `/api/clientes/{id}` | Consulta um cliente pelo id |

### Pedidos — `/api/pedidos`
Cadastro e consulta de pedidos vinculados a um cliente.

| Método | Rota | Resumo |
|---|---|---|
| POST | `/api/pedidos` | Cadastra um novo pedido vinculado a um cliente já existente |
| GET | `/api/pedidos` | Lista todos os pedidos cadastrados |
| GET | `/api/pedidos/{id}` | Consulta um pedido pelo id |

### Pagamentos — `/api/pagamentos`
Geração e consulta de cobranças junto ao Asaas.

| Método | Rota | Resumo |
|---|---|---|
| POST | `/api/pagamentos/{pedidoId}` | Gera uma cobrança (PIX ou boleto) para um pedido existente |
| GET | `/api/pagamentos` | Lista o histórico de todos os pagamentos gerados |
| GET | `/api/pagamentos/{id}` | Consulta o status atual de um pagamento pelo id |

### Pix — `/api/pix`
Gestão de chaves Pix e QR Codes estáticos junto ao Asaas.

| Método | Rota | Resumo |
|---|---|---|
| POST | `/api/pix/chaves` | Cria uma chave Pix (por padrão do tipo EVP, aleatória) |
| POST | `/api/pix/qrcodes/estatico` | Gera um QR Code Pix estático vinculado a uma chave existente |
| DELETE | `/api/pix/qrcodes/estatico/{id}` | Remove um QR Code Pix estático pelo id |

### Webhook Asaas — `/payments-webhook`
Endpoint chamado pelo Asaas (não pelo front-end) para notificar mudanças de status de pagamento.

| Método | Rota | Resumo |
|---|---|---|
| POST | `/payments-webhook` | Recebe a notificação, publica no Kafka e responde imediatamente; o processamento (atualização do status) acontece de forma assíncrona em um consumer |

## Processamento assíncrono do webhook (Kafka)

Quando o Asaas notifica uma mudança de status, o controller apenas publica o evento no tópico `pagamento.webhook-recebido` e responde `200 OK` na hora. Um consumer separado (`WebhookConsumer`) processa a mensagem em segundo plano e atualiza o pagamento no banco. Em caso de falha no processamento, há 3 tentativas automáticas; se todas falharem, a mensagem vai para uma fila de erro (`pagamento.webhook-recebido.DLT`) em vez de ser perdida.

## Equipe

Alexandre Gonçalves, Mariane Mireli, Wenderson Dhomini
