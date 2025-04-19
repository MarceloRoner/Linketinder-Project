# 🏆 Linketinder Project

Projeto desenvolvido em **Groovy** para o **ZG-HERO (K1-T4)**. Trata-se de um **MVP para simular um sistema de recrutamento estilo "LinkedIn + Tinder."**

![GitHub repo size](https://img.shields.io/github/repo-size/MarceloRoner/Linketinder-Project)
![GitHub last commit](https://img.shields.io/github/last-commit/MarceloRoner/Linketinder-Project)

---

## 🚀 Como Executar

1. Certifique-se de ter o [Groovy](https://groovy-lang.org/) instalado.
2. Clone este repositório:
   ```bash
   git clone https://github.com/MarceloRoner/Linketinder-Project.git
   ```
3. Dentro da pasta do projeto, vá até `src/main/groovy` e execute:

   ```bash
   groovy Main.groovy
   ```

> Siga as instruções no terminal para cadastrar ou visualizar candidatos, empresas, vagas e matches.

---

## 🔧 Arquitetura Baseada em Camadas

O sistema foi refatorado para seguir boas práticas de projeto, com separação clara de responsabilidades.

### ✨ Princípios Aplicados (SOLID & Clean Code)
- **SRP** – Cada classe tem uma única responsabilidade
- **OCP** – Código aberto para extensão, fechado para modificação
- **LSP** – Substituição de implementações via interfaces DAO
- **ISP** – Interfaces específicas por entidade
- **DIP** – `AppContext` injeta dependências para os serviços e DAOs

---

## 🧱 Estrutura de Pastas

```
src/
├── main/
│   ├── app/           # CLI (LinketinderApp) como orquestrador da aplicação
│   ├── context/       # AppContext com injeção manual de dependências
│   ├── dao/           # Interfaces e implementações JDBC (PostgreSQL)
│   ├── domain/        # Entidades: Candidato, Empresa, Vaga, Curtida
│   ├── service/       # Regras de negócio por entidade
│   └── Main.groovy    # Bootstrap da aplicação
│
├── test/
│   └── service/       # Testes unitários com Spock
```

---

## 🧪 Testes Automatizados com Spock

- Testes unitários para cada service (`src/test/groovy/service/`)
- Mock de DAOs via Spock
- Testes de integração com banco de dados real

### Rodando os testes:

```bash
gradle test
```

Relatório em: `build/reports/tests/test/index.html`

---

## 🛢️ Integração com PostgreSQL

- Script `linketinder_project.sql` para criação do schema
- Relacionamentos N:N via tabelas pivot
- CRUD completo com JDBC
- DAO com interface e implementação desacopladas
- `AppContext` gerencia instâncias dos DAOs e Services

### Configuração do banco:

1. Crie o banco `linketinder`
2. Rode o script SQL
3. Atualize os dados de conexão no DAO se necessário

---

## ❤️ Sistema de Curtidas e Match

- Candidatos podem curtir empresas, e vice-versa
- Quando o match é mútuo, o sistema exibe automaticamente
- Registros de curtidas e matches persistidos no banco
- Regra de negócio centralizada em `CurtidaService`

---

## 🌐 Frontend Simples (TypeScript)

- Protótipo de frontend com validações
- Simulação de cadastro, listagem e login no `localStorage`

```bash
cd frontend/
npm install
npm run build
npm start
```

---

## ✅ Destaques da Refatoração

- ✅ Separação entre camadas de domínio, DAO, service e aplicação
- ✅ Inversão de dependências com `AppContext`
- ✅ DAOs desacoplados com interfaces
- ✅ Testes unitários e de integração com Spock
- ✅ Bootstrap via `Main.groovy` com inicialização clara
- ✅ CLI modular via `LinketinderApp.groovy`
- ✅ README atualizado e documentado

---

**Feito com Groovy, PostgreSQL e Clean Code por Marcelo Roner.**  
**Powered by ZG Soluções | Acelera ZG** 💥
