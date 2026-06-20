[README.md](https://github.com/user-attachments/files/29154531/README.md)
# A3_MMTES — Ticket SIR

Trabalho avaliativo da disciplina **Modelos, Métodos e Técnicas de Engenharia de Software** (MMTES) — UniBH, 2026/2.

API REST para gestão de tickets do **SIR (Sistema de Intervenção de Rede)** de uma empresa de internet corporativa. O sistema cadastra atividades de rede (obras, manutenções, remanejamentos), associa circuitos de clientes afetados e dispara notificações automáticas por e-mail no ciclo de vida do ticket.

## Stack

- **Java 21**
- **Spring Boot 4.0.7** (Web, Data JPA, Security, Mail, Thymeleaf, Validation)
- **PostgreSQL 16+**
- **Hibernate Envers** (auditoria de revisões)
- **JUnit 5 + Mockito** (testes unitários)
- **Maven** (build)

## Estrutura

```
A3_MMTES/
├── API/ticketApi/         ← projeto Spring Boot
│   ├── src/main/java      ← código de produção
│   ├── src/test/java      ← testes unitários (14 testes)
│   ├── .env.examples      ← template de variáveis de ambiente
│   └── pom.xml
└── Documentação/          ← requisitos e mapeamento
```

## Pré-requisitos

- Java 21+ (`java -version`)
- PostgreSQL 16+ rodando localmente
- (Opcional) IntelliJ IDEA / VS Code com Extension Pack for Java

## Configuração

### 1. Banco de dados

Crie um banco vazio no PostgreSQL chamado `ticket_sir` (via pgAdmin ou psql). As tabelas são criadas automaticamente pelo Hibernate na primeira execução.

### 2. Variáveis de ambiente

Na pasta `API/ticketApi/`, copie o template:

```powershell
copy .env.examples .env
```

Edite o `.env` com seus valores reais:

```
DB_URL=jdbc:postgresql://localhost:5432/ticket_sir
DB_USER=postgres
DB_PASS=sua_senha_do_postgres
MAIL_USER=seu_email@gmail.com
MAIL_PASS=sua_senha_de_app
API_ADMIN_USER=admin
API_ADMIN_PASS=admin
```

> O `.env` está no `.gitignore` — não versione suas credenciais.

## Como rodar a API

Na pasta `API/ticketApi/`:

```powershell
.\mvnw.cmd spring-boot:run
```

A API sobe em `http://localhost:8080` com autenticação HTTP Basic (`API_ADMIN_USER` / `API_ADMIN_PASS`).

### Endpoints principais

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/tickets` | Criar ticket |
| `GET` | `/tickets` | Listar tickets |
| `GET` | `/tickets/{id}` | Buscar por ID |
| `PUT` | `/tickets/{id}` | Atualizar ticket |
| `PATCH` | `/tickets/{id}/status?status=...` | Alterar status (máquina de estados) |
| `POST` | `/tickets/{id}/circuitos` | Associar circuito |
| `POST` | `/tickets/{id}/circuitos/importar` | Importar circuitos em massa |
| `GET` | `/tickets/{id}/circuitos` | Listar circuitos do ticket |
| `DELETE` | `/tickets/{id}/circuitos/{circuitoId}` | Desassociar circuito |

## Como rodar os testes

O projeto contém **14 testes unitários** (Mockito puro, sem necessidade de banco):

- `CircuitoServiceTest` — 5 testes
- `EmailServiceTest` — 4 testes
- `TicketServiceTest` — 5 testes

### Pelo Maven (CLI)

```powershell
.\mvnw.cmd test
```

### Pelo IntelliJ IDEA

1. **File → Open** e selecione a pasta `API/ticketApi/`
2. Aguarde a indexação do Maven
3. Botão direito no pacote `br.mmtes.ticketApi.service` → **Run 'Tests in service'**

### Pelo VS Code

1. Instale o **Extension Pack for Java**
2. Clique no botão ▶ ao lado de cada `@Test` ou abra o painel **Testing**

**Resultado esperado:**
```
Tests run: 14, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## Arquitetura

O projeto aplica deliberadamente **SOLID**, **Design Patterns** e **Testes Unitários** como foco pedagógico.

### Design Patterns

- **Observer** — `StatusAlteradoEvent` + `EmailNotificacaoListener` desacoplam mudança de status do envio de notificações
- **Strategy** — `NotificacaoStrategy` permite adicionar novos canais (WhatsApp, SMS) sem alterar código existente
- **Builder** — `NotificacaoEmail` construído de forma fluente e imutável
- **Repository** — Spring Data JPA fornece acesso a dados sem boilerplate

### Princípios SOLID

| Letra | Aplicação |
|---|---|
| **S** | Cada serviço (`TicketService`, `CircuitoService`, `EmailService`) tem uma única responsabilidade |
| **O** | `NotificacaoStrategy` aberta para extensão, fechada para modificação |
| **L** | Qualquer implementação de `NotificacaoStrategy` substitui `EmailNotificacaoStrategy` sem quebrar o `EmailService` |
| **I** | `NotificacaoStrategy` define apenas o método `enviar` |
| **D** | Serviços dependem de interfaces, recebem dependências via construtor (injeção pelo Spring) |

### Máquina de estados do ticket

```
ABERTO              → AGUARDANDO_ANALISE, CANCELADO
AGUARDANDO_ANALISE  → EM_ANALISE, CANCELADO
EM_ANALISE          → NOTIFICADO, CANCELADO
NOTIFICADO          → AGUARDANDO_EXECUÇÃO, CANCELADO
AGUARDANDO_EXECUÇÃO → EM_EXECUÇÃO, PARALISADO, CANCELADO
EM_EXECUÇÃO         → FECHADO, PARALISADO
PARALISADO          → AGUARDANDO_EXECUÇÃO, CANCELADO
```

Transições inválidas lançam `IllegalStateException` (HTTP 400).

## Documentação adicional

- `Documentação/Mapeamento-Requisitos-SIR.html` — mapeamento de requisitos
- `Documentação/Pendencias-Ticket_SIR-REV.02.pdf` — pendências consideradas
