# Diaghealthy

Sistema de gestão hospitalar baseado em microsserviços, desenvolvido para o **Tech Challenge Fase 3** (Pós Tech FIAP — Arquitetura e Desenvolvimento Java). Cobre agendamento de consultas, histórico médico, notificações automáticas de lembrete e controle de acesso por perfil (médico, enfermeiro, paciente), com comunicação assíncrona via RabbitMQ e consultas flexíveis via GraphQL.

## Arquitetura

Quatro microsserviços independentes, cada um seguindo Clean Architecture (`domain` → `application` → `infrastructure`), com banco Postgres próprio:

| Serviço | Porta | Responsabilidade |
|---|---|---|
| `diaghealthy_users` | 8080 | Cadastro de médicos/enfermeiros/pacientes, autenticação e emissão de JWT |
| `diaghealthy_scheduling` | 8081 | Criação e edição de agendamentos de consulta |
| `diaghealthy_notification` | 8082 | Envio (simulado) de lembretes de consulta ao paciente |
| `diaghealthy_history` | 8083 | Histórico de consultas, consultável via REST e GraphQL |


Cada serviço tem seu próprio Postgres (`users-db`, `scheduling-db`, `notification-db`, `history-db`), todos orquestrados via `docker-compose.yml` na raiz do projeto, junto com o RabbitMQ.

## Autenticação e perfis de acesso

Autenticação via **JWT** (emitido pelo `diaghealthy_users`, validado por todos os serviços com o mesmo segredo compartilhado). O token carrega o papel (`role`) e o ID do usuário logado.

| Perfil | Permissões principais |
|---|---|
| `ADMIN` | Acesso completo a todos os cadastros e recursos |
| `DOCTOR` (Médico) | Visualiza e edita o histórico de consultas; registra/edita agendamentos |
| `NURSE` (Enfermeiro) | Registra consultas, acessa o histórico, cadastra pacientes |
| `PATIENT` (Paciente) | Visualiza apenas as próprias consultas, notificações e histórico |

Usuários de teste já vêm carregados (seed) no `diaghealthy_users`:

| E-mail | Senha | Papel |
|---|---|---|
| admin@email.com | admin123 | ADMIN |
| doctor@email.com | doctor123 | DOCTOR |
| nurse@email.com | nurse123 | NURSE |
| patient@email.com | patient123 | PATIENT |

## Comunicação assíncrona (RabbitMQ)

Ao criar ou editar um agendamento, o `diaghealthy_scheduling` publica um evento (`appointment.created` ou `appointment.updated`) numa exchange direta do RabbitMQ. Dois consumidores reagem de forma independente:

- **`diaghealthy_notification`** registra e simula o envio de um lembrete por e-mail ao paciente.
- **`diaghealthy_history`** registra o atendimento no histórico — uma edição do agendamento **atualiza** o registro existente (por `appointmentId`), em vez de duplicar.

Painel de administração do RabbitMQ disponível em `http://localhost:15672` (usuário/senha definidos no `.env`).

## Consultas flexíveis via GraphQL

O `diaghealthy_history` expõe `POST /graphql`, com duas consultas:

- `medicalRecord(id: ID!)` — busca um registro específico do histórico.
- `medicalRecordsByPatient(patientId: ID!, onlyFuture: Boolean)` — lista o histórico de um paciente, com opção de filtrar apenas as consultas futuras.

Interface interativa (GraphiQL) disponível em `http://localhost:8083/graphiql`, sem necessidade de autenticação para carregar a página (as consultas em si continuam exigindo o JWT).

## Como executar

Pré-requisitos: Docker e Docker Compose.

```bash
# na raiz do projeto
cp .env.example .env   # ajuste as credenciais se quiser
docker compose up -d --build
```

Isso sobe os 4 microsserviços, os 4 bancos Postgres e o RabbitMQ. Ao final, os serviços ficam disponíveis em `localhost:8080` a `localhost:8083`.

Para derrubar tudo:

```bash
docker compose down
```

> **Observação:** os serviços foram configurados para rodar via Docker Compose (usam os hostnames internos dos containers Postgres). Rodar um serviço isoladamente fora do Compose (ex.: `mvnw spring-boot:run` direto na IDE) exige ajustar `spring.datasource.url` para um Postgres acessível localmente.

## Documentação da API

Cada serviço expõe Swagger/OpenAPI, com suporte a autenticação Bearer JWT direto pela interface (botão **Authorize**):

- Usuários: `http://localhost:8080/swagger-ui.html`
- Agendamento: `http://localhost:8081/swagger-ui.html`
- Notificação: `http://localhost:8082/swagger-ui.html`
- Histórico: `http://localhost:8083/swagger-ui.html`

## Testando com Postman

O arquivo [`Diaghealthy.postman_collection.json`](./Diaghealthy.postman_collection.json), na raiz do projeto, cobre o fluxo completo dos 4 serviços (autenticação, CRUD de usuários, agendamento, notificações, histórico via REST e via GraphQL). Basta importar no Postman e rodar as pastas em ordem — tokens e IDs são capturados automaticamente entre as requisições.

## Stack técnica

- Java 26 / Spring Boot 4.0.8
- Spring Security + JWT (`java-jwt`)
- Spring Data JDBC
- Spring for GraphQL
- Spring AMQP (RabbitMQ)
- PostgreSQL
- springdoc-openapi (Swagger)
- Docker / Docker Compose
