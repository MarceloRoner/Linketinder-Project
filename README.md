# 🏆 Linketinder Project

Projeto desenvolvido em **Groovy** (back-end) + **TypeScript** (front-end) para o **ZG-HERO**.  
É um **MVP** que simula um sistema de recrutamento no estilo **LinkedIn + Tinder**.

![GitHub repo size](https://img.shields.io/github/repo-size/MarceloRoner/Linketinder-Project)
![GitHub last commit](https://img.shields.io/github/last-commit/MarceloRoner/Linketinder-Project)

---

## 🚀 Como executar

### Back-end (CLI)
```bash
git clone https://github.com/MarceloRoner/Linketinder-Project.git
cd Linketinder-Project
./gradlew run               # ou gradle run
```

### Front-end (SPA estático)
```bash
cd frontend
npm install
npm run build               # compila para dist/
npx serve .                 # servidor estático para testar
```

## 🔧 Arquitetura

| Camada / pacote      | Descrição |
|----------------------|-----------|
| `view/`              | LinketinderView (CLI – camada View do MVC) |
| `controller/`        | Orquestram requisições da View → Facade |
| `facade/`            | API de alto nível para UI (LinketinderFacade) |
| `service/`           | Regras de negócio (SRP) |
| `dao/`               | Persistência JDBC (PostgreSQL) |
| `model/`             | Entidades ricas |
| `context/AppContext` | Injeta dependências (Factory + DIP) |
| `utils/ConnectionFactory` | Factory + Singleton da `java.sql.Connection` |

**Front-end (`src`)**
```
src
├── model/        # DTO/Tipos
├── service/      # api.ts, storageService, validations
├── controller/   # cadastros + binds de eventos
└── view/         # renders/perfis
```

## ✨ SOLID & Design Patterns

| Padrão / Princípio | Onde foi aplicado |
|----------------------|------------------|
| Factory + Singleton  | `utils.ConnectionFactory` cria uma única conexão JDBC |
| Facade               | `LinketinderFacade` desacopla View da business-logic |
| MVC                  | Backend (CLI = View, Controllers, Model/Service/DAO) e Front-end (HTML + TS) |
| DIP / ISP            | Services dependem de interfaces DAO |

## 🧪 Testes (Spock)
```bash
./gradlew test
open build/reports/tests/test/index.html
```
Stubs de DAO garantem testes rápidos e isolados para todos os services.

## 📂 Configuração do PostgreSQL
- Crie o banco `linketinder`
- Rode o script `linketinder_schema.sql`
- Ajuste URL/usuário em `utils/ConnectionFactory.groovy` se necessário

## 💖 Lógica de Match
- **Candidato** curte **Vaga** → registro em memória
- **Empresa** curte **Candidato** → se recíproco ⇒ **MATCH!**
- Implementado em `CurtidaService`

## 🌐 Front-end (MVC + TypeScript)

| Pasta | Destaque |
|-------|----------|
| `service/api.ts`  | CRUD genérico → hoje usa localStorage, pronto para REST |
| `controller/*`    | coleta dados do formulário, valida e chama api |
| `view/*`          | renderiza tabelas e gráficos |
| `css/style.css`   | tema moderno, responsivo, acessibilidade |

## ✅ Principais ganhos da refatoração
- Conexão única ao BD (Factory + Singleton)
- Separação clara MVC em ambos os lados
- UI (CLI e HTML) livre de SQL/DAO
- Código de fácil extensão (ex.: trocar JDBC por JPA; trocar localStorage por REST)
- Testes verdes garantindo regras de negócio

---

Desenvolvido por Marcelo Roner — Groovy ✦ TypeScript ✦ PostgreSQL ✦ Clean Code