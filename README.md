# 🏆 Linketinder Project

Projeto desenvolvido em **Groovy** para o **ZG-HERO (K1-T4)**. Trata-se de um **MVP para simular um sistema de recrutamento estilo "LinkedIn + Tinder."**.

![GitHub repo size](https://img.shields.io/github/repo-size/MarceloRoner/Linketinder-Project)
![GitHub last commit](https://img.shields.io/github/last-commit/MarceloRoner/Linketinder-Project)

## Como Executar
1. Certifique-se de ter o [Groovy](https://groovy-lang.org/) instalado.
2. Clone este repositório:
   ```bash
   git clone https://github.com/MarceloRoner/Linketinder-Project.git
   ```
3. Dentro da pasta do projeto, vá até `src/main/groovy` e execute:

```bash
groovy LinketinderApp.groovy
```

Siga as instruções do menu no terminal para listar ou cadastrar candidatos/empresas.

Ou, se preferir, configure um **Gradle** para compilar e rodar testes. Para um MVP rápido, basta rodar direto pelo Groovy.

---

## 🔄 Refatoração com Clean Code e Arquitetura em Camadas

### ✨ Princípios Aplicados
- **SRP (Single Responsibility Principle)**: cada classe tem uma única responsabilidade
- **DRY (Don't Repeat Yourself)**: lógica reutilizada nos Services e DAOs
- **Desacoplamento**: o App não depende diretamente dos DAOs
- **Coesão**: responsabilidades agrupadas por módulo lógico
- **Lei de Demeter**: o App se comunica apenas com seus "amigos diretos" (services)

### 🧱 Camadas do Projeto
- `domain/` → Entidades do sistema (Candidato, Empresa, Vaga, Curtida)
- `dao/` → Acesso ao banco de dados (PostgreSQL via JDBC)
- `service/` → Regras de negócio e coordenação das entidades
- `main/` → Interface CLI (`LinketinderApp`) como orquestrador
- `test/` → Testes automatizados com Spock Framework

---

## 🛢️ Integração com PostgreSQL
- Todos os dados persistidos via SQL real
- Relacionamentos N:N modelados com tabelas intermediárias
- Script SQL disponível para criação do schema (`linketinder_project.sql`)
- Dados de exemplo incluídos para testes

---

## ❤️ Sistema de Curtidas
- Candidatos e empresas podem curtir
- Se ambos curtirem → **MATCH!**
- Curtidas atualmente mantidas em memória
- Lógica encapsulada na classe `CurtidaService`

---

## 🧪 Testes Automatizados (Spock)
- Serviços testados com Spock (`src/test/groovy/service/*.groovy`)
- Testes de integração com banco real e lógica de match

### Rodando os testes:
```bash
gradle test
```
Acesse o relatório em: `build/reports/tests/test/index.html`

---

## 🌐 Frontend (Independente - Em TypeScript)
- Frontend de testes com validação e simulação em `localStorage`
- Simula cadastro e listagem de usuários
- Validações com Regex

```bash
cd frontend/
npm install
npm run build
npm start
```

---

## 🧮 Banco de Dados
### Como usar:
1. Crie o banco `linketinder` no PostgreSQL
2. Rode o script `linketinder_project.sql`
3. Use o modelo `linketinder_project.png` para visualizar a modelagem

---

## ✅ Contribuições Recentes
- Refatoração completa com Clean Code
- Aplicação de SRP, DRY, Demeter e modularização
- Testes Spock com integração real
- Interface CLI desacoplada e simples
- README atualizado para refletir a nova arquitetura

---

**Feito por Marcelo Roner com paixão, Groovy e Clean Code.** 🚀
