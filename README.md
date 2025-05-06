# 🏆 Linketinder Project

Projeto desenvolvido em **Groovy** (back-end) + **TypeScript** (front-end) para o **ZG-HERO**.  
É um **MVP** que simula um sistema de recrutamento no estilo **LinkedIn + Tinder**.

![GitHub repo size](https://img.shields.io/github/repo-size/MarceloRoner/Linketinder-Project)
![GitHub last commit](https://img.shields.io/github/last-commit/MarceloRoner/Linketinder-Project)

---

## 🌐 API REST (ZG-HERO Trilha K2-T6)

A API REST foi construída sem uso de frameworks como Spring ou Micronaut, utilizando apenas **Groovy + HttpServer puro**, respeitando as boas práticas RESTful (status HTTP, JSON, estrutura MVC).

### 🚀 Endpoints disponíveis:

| Método | Rota            | Descrição                         |
|--------|------------------|-----------------------------------|
| GET    | `/ping`         | Teste de disponibilidade (`pong`) |
| GET    | `/candidatos`   | Lista todos os candidatos         |
| POST   | `/candidatos`   | Cadastra um novo candidato        |
| GET    | `/empresas`     | Lista todas as empresas           |
| POST   | `/empresas`     | Cadastra uma nova empresa         |
| GET    | `/vagas`        | Lista todas as vagas              |
| POST   | `/vagas`        | Cadastra uma nova vaga (valida empresa) |

### 📦 Como executar (API)

```bash
git clone https://github.com/MarceloRoner/Linketinder-Project.git
cd Linketinder-Project
./gradlew run        # ou gradle run
```

Depois, acesse `http://localhost:8080` para testar os endpoints via Postman ou curl.

---

## 🔧 Arquitetura

| Camada / pacote      | Descrição |
|----------------------|-----------|
| `boot/`              | Inicializa o servidor HTTP e configura rotas REST |
| `controller.rest/`   | Handlers REST para candidatos, empresas, vagas |
| `model/`             | Classes de domínio (`Candidato`, `Empresa`, `Vaga`) |
| `dao/`               | Acesso a dados (JDBC) |
| `service/`           | Regras de negócio (validações, persistência) |
| `utils/`             | `ConnectionFactory` (JDBC Singleton) + `LocalDateAdapter` |
| `frontend/`          | SPA com TypeScript, HTML e CSS |

---

## ✨ SOLID & Design Patterns

| Padrão / Princípio | Onde foi aplicado |
|--------------------|-------------------|
| Factory + Singleton | `ConnectionFactory` (JDBC única) |
| MVC                | Separação clara entre Controller, Model, Service e DAO |
| JsonHandler Abstrato | Facilita resposta e leitura JSON em todos os handlers |
| Adapter            | `LocalDateAdapter` para integrar `LocalDate` com JSON |
| DIP                | Services não conhecem JDBC, só DAO |

---

## 🧪 Testes (Spock)
```bash
./gradlew test
```
Stubs e Mocks são usados para testar regras de negócio isoladamente.

---

## 📂 Banco de Dados (PostgreSQL)
- Crie o banco `linketinder`
- Execute o script `linketinder_schema.sql` (DDL)
- Configure login/URL em `ConnectionFactory.groovy`

---

## 💖 Lógica de Match
- Candidato curte vaga
- Empresa curte candidato
- Se for mútuo: **MATCH**
- Implementado em `CurtidaService`

---

## 💻 Front-end SPA (TypeScript + MVC)

| Pasta | Função |
|-------|--------|
| `service/api.ts`  | Lida com chamadas REST ou localStorage |
| `controller/*`    | Valida formulário e envia dados |
| `view/*`          | Renderiza HTML e perfis |
| `css/style.css`   | Layout moderno e responsivo |

---

## ✅ Ganhos da refatoração

- API RESTful pura, sem frameworks
- Separação clara de responsabilidades (MVC)
- Conexão única com o banco
- Código limpo, desacoplado e testável
- Pode facilmente evoluir para Swagger, Spring Boot, JPA ou MongoDB

---

Desenvolvido por Marcelo Roner — Groovy ✦ TypeScript ✦ PostgreSQL ✦ REST ✦ Clean Code
