# 🏆 Linketinder Project

Projeto desenvolvido em **Groovy** para o **ZG-HERO (K1-T4)**.  
É um **MVP** que simula um sistema de recrutamento estilo **LinkedIn + Tinder**.

![GitHub repo size](https://img.shields.io/github/repo-size/MarceloRoner/Linketinder-Project)
![GitHub last commit](https://img.shields.io/github/last-commit/MarceloRoner/Linketinder-Project)

---

## 🚀 Como Executar

1. Tenha [Java 17+](https://adoptium.net/) e [Groovy 4+](https://groovy-lang.org/) instalados.  
2. Clone o repositório:

   ```bash
   git clone https://github.com/MarceloRoner/Linketinder-Project.git
   cd Linketinder-Project
   ```

3. Execute a aplicação (Gradle wrapper incluso):

   ```bash
   ./gradlew run         # ou gradle run
   ```

No terminal você poderá cadastrar ou visualizar candidatos, empresas, vagas e matches.

---

## 🔧 Arquitetura em Camadas

| Camada / pacote | Descrição |
|------------------|-----------|
| `app`           | CLI (`LinketinderApp`) – interação com o usuário |
| `facade`        | `LinketinderFacade` – porta de entrada única para a UI |
| `context`       | `AppContext` – constrói DAOs, Services e a Facade |
| `service`       | Regras de negócio (candidato, empresa, vaga, curtida) |
| `dao`           | Persistência JDBC (PostgreSQL) via interfaces DAO |
| `domain`        | Entidades ricas (`Candidato`, `Empresa`, `Vaga`, etc.) |
| `utils`         | Infraestruturas (ex.: `ConnectionFactory`) |

---

## ✨ Princípios Aplicados (SOLID & Clean Code)

- **SRP** – cada classe possui responsabilidade única  
- **OCP** – novas features via extensão (ex.: novo DAO)  
- **LSP / ISP** – implementações substituem as interfaces DAO sem quebrar contrato  
- **DIP** – UI depende da Facade; Services dependem de interfaces DAO  

---

## 🏗️ Design Patterns implementados

| Padrão        | Onde / por que |
|---------------|----------------|
| **Factory + Singleton** | `utils.ConnectionFactory` cria e reutiliza uma única `Connection`, removendo duplicação de código e melhorando performance |
| **Facade**     | `facade.LinketinderFacade` oferece métodos de alto nível como `listarCandidatos()` ou `cadastrarVaga()`, desacoplando a UI da lógica interna |

---

## 🧱 Estrutura de Pastas

```
src
├── main
│   ├── app/            LinketinderApp (CLI)
│   ├── context/        AppContext (injeção manual)
│   ├── facade/         LinketinderFacade
│   ├── dao/            JDBC DAOs + interfaces
│   ├── domain/         Entidades
│   ├── service/        Camada de negócio
│   └── Main.groovy     Bootstrap
└── test
    └── service/        Testes unitários (Spock)
```

---

## 🧪 Testes Automatizados (Spock)

```bash
./gradlew test
open build/reports/tests/test/index.html
```

- Stubs de DAO → testes rápidos e isolados  
- Cobrem serviços, curtidas e regras de negócio  

---

## 🛢️ Banco de Dados PostgreSQL

- Crie o banco `linketinder`  
- Importe o `linketinder_schema.sql` (na raiz)  
- Ajuste URL/usuário/senha em `utils/ConnectionFactory.groovy` se necessário  

---

## ❤️ Sistema de Curtidas e Match

- Candidatos podem curtir vagas  
- Empresas podem curtir candidatos  
- Quando ambos curtirem → a aplicação exibe **MATCH!**  
- Lógica centralizada no `CurtidaService`  

---

## ✅ Destaques da Refatoração

- Conexão única com **Factory + Singleton**
- UI desacoplada via **Facade**
- Inversão de dependências no `AppContext`
- DAOs 100% orientados a **interfaces**
- Testes Spock verdes (`./gradlew test`)
- Código limpo e documentado

---

Desenvolvido por **Marcelo Roner** – Groovy, PostgreSQL & Clean Code
