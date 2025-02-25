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

groovy LinketinderApp.groovy
Siga as instruções do menu no terminal para listar ou cadastrar candidatos/empresas.


Ou, se preferir, configurar um **Gradle** ou **Maven** para compilar. Mas, para um MVP rápido, basta rodar direto pelo `groovy LinketinderApp.groovy`.

---

## Nova Feature: Sistema de Curtidas
- Agora o projeto inclui uma classe `Curtida` que relaciona:
  - O Candidato e a Vaga que ele curtiu.
  - Uma Empresa que também curtiu esse Candidato.
- Quando ambas as partes curtem (Candidato a Vaga, Empresa ao Candidato), ocorre o **Match**.
- Para testar:
  1. Rode `groovy LinketinderApp.groovy`.
  2. Escolha a opção "4) Candidato Curte Vaga".
  3. Escolha a opção "5) Empresa Curte Candidato".
  4. Vá em "6) Exibir Curtidas" para ver o status de cada curtida e verificar se há match.

## Nova Feature: Testes unitários
  Agora o projeto conta com testes unitários escritos em Groovy, usando o framework Spock. Para rodar:

1. Instale Gradle (ou use o wrapper, caso tenha configurado).
2. Na raiz do projeto, rode `gradle test`.
3. Verifique o relatório em `build/reports/tests/test/index.html`.

Caso prefira rodar manualmente, é possível usar `groovy testRunner.groovy` (ou algo que você tenha configurado).

## Executando o Frontend (TypeScript)

1️⃣ Preparação do ambiente
Antes de iniciar o projeto, certifique-se de que possui o Node.js instalado.

2️⃣ Instalando as dependências
Abra o terminal e, dentro da pasta do frontend, execute:


cd frontend
npm install
Isso instalará todas as dependências necessárias para rodar o projeto.

3️⃣ Compilando o TypeScript
Para compilar os arquivos TypeScript e gerar o código JavaScript na pasta dist/, execute:


npm run build
⚠️ A pasta dist/ é ignorada pelo Git, pois contém apenas os arquivos gerados automaticamente.

4️⃣ Rodando o servidor local
Se quiser visualizar o projeto no navegador com reload automático, utilize o comando:

npm start
Isso abrirá a aplicação no navegador utilizando o lite-server.

5️⃣ Estrutura do Frontend
Os arquivos HTML do projeto estão localizados na pasta:

frontend/html/
Principais páginas disponíveis:

cadastro-candidato.html → Cadastro de candidatos
cadastro-empresa.html → Cadastro de empresas
cadastro-vaga.html → Cadastro de vagas
perfil-candidato.html → Perfil dos candidatos
perfil-empresa.html → Perfil das empresas
6️⃣ Importante
📌 Nesta etapa, o frontend ainda não está integrado ao backend em Groovy. No momento, os dados são armazenados localmente no navegador utilizando localStorage.
📌 A comunicação real com o backend será implementada no KIT 2.





