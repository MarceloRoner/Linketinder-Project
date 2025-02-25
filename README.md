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

1. Entre na pasta `frontend/`.
2. Instale as dependências:
   ```bash
   npm install
Compile os arquivos TypeScript:
npm run build
Isso gera a pasta dist/ (ignorada pelo Git).
Se quiser rodar um servidor local com reload automático:
npm start
Isso abrirá no navegador (via lite-server).
As páginas HTML ficam em frontend/html/. Exemplos:
cadastro-candidato.html
cadastro-empresa.html
cadastro-vaga.html
perfil-candidato.html
perfil-empresa.html 
Cada página carrega o .js compilado de dist/.
Observe que, neste momento, não há integração real com o backend Groovy — estamos usando localStorage. No KIT 2 faremos a comunicação frontend/backend.



