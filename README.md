
# 🏆 Linketinder Project 

Projeto desenvolvido em **Groovy** para o **ZG-HERO (K1-T4)**. Trata-se de um **MVP para simular um sistema de recrutamento estilo "LinkedIn + Tinder."**.

![GitHub repo size](https://img.shields.io/github/repo-size/MarceloRoner/Linketinder-Project)
![GitHub last commit](https://img.shields.io/github/last-commit/MarceloRoner/Linketinder-Project)

## Como Executar
1. Certifique-se de ter o [Groovy](https://groovy-lang.org/) instalado.
2. Clone este repositório:
   git clone https://github.com/<seu-usuario>/Linketinder-Project.git
Dentro da pasta do projeto, vá até src/main/groovy.
Execute o comando:

```
groovy LinketinderApp.groovy
```

Siga as instruções do menu no terminal para listar ou cadastrar candidatos/empresas.

Ou, se preferir, configurar um **Gradle** ou **Maven** para compilar. Mas, para um MVP rápido, basta rodar direto pelo `groovy LinketinderApp.groovy`.

---

## 🔄 Nova Lógica com Integração a Banco de Dados

### 🗂️ Organização por Módulos

O projeto foi reorganizado em **módulos separados**, com os arquivos distribuídos conforme suas responsabilidades:

- `dao/` → Acesso a dados com JDBC + PostgreSQL
- `domain/` → Entidades principais: `Candidato`, `Empresa`, `Vaga`, etc.
- `main/` → Aplicação principal (`LinketinderApp`) e lógica de curtidas (`Curtida`)

### 🛢️ Integração com PostgreSQL

A lógica antiga com listas fixas foi substituída por uma camada DAO com SQL real:

- Inserção e busca de candidatos/empresas via PostgreSQL
- Relacionamento N:N com tabelas intermediárias (`candidato_competencia`, etc.)
- Evita duplicidade de competências com a função `inserirOuBuscarCompetencia`
- Dados agora são **persistidos no banco de dados**

---

## ❤️ Sistema de Curtidas

- A classe `Curtida` permite:
  - Candidato curtir uma vaga
  - Empresa curtir um candidato
  - Se ambos curtirem → **MATCH!**
- As curtidas ainda são mantidas em memória por enquanto
- Visualize o status atual das curtidas pelo menu

---

## 🧪 Testes Unitários (Spock)

- Testes escritos em **Groovy** com o framework **Spock**
- Para rodar:
  ```bash
  gradle test
  ```
- Verifique o relatório em `build/reports/tests/test/index.html`

---

## 🌐 Frontend em TypeScript

O projeto possui um frontend independente que simula as funcionalidades do sistema com dados em `localStorage`.

### Como rodar:

1. Instale o Node.js
2. Acesse a pasta `frontend/` e rode:
   ```bash
   npm install
   npm run build
   npm start
   ```

### Estrutura:

- `cadastro-candidato.html`, `cadastro-empresa.html`, etc.
- Dados armazenados no navegador (ainda sem integração com backend)
- Validações via Regex para todos os campos

---

## 🧮 Banco de Dados: Script e Modelagem

### Como usar:
1. Crie o banco `linketinder` no pgAdmin ou terminal
2. Rode `linketinder_project.sql`
3. Visualize a modelagem no arquivo `linketinder_project.png`

### Tabelas principais:
- `candidato`, `empresa`, `competencia`, `vaga`
- Relacionamentos N:N com tabelas pivot
- Tabela `curtida` (em desenvolvimento para futura persistência)

---

## ✅ Contribuições recentes

- Reestruturação total do projeto
- Lógica principal com DAO e SQL real
- Menu funcional com integração de dados reais
- Atualização do README e boas práticas com commit semântico
