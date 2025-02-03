# Linketinder Project

## Descrição
Projeto desenvolvido em Groovy para o ZG-HERO (K1-T4). Trata-se de um MVP para simular um sistema de recrutamento estilo “Linketinder”.

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

## 7. Próximos Passos / Dicas

- **Persistência de dados**: Por enquanto, tudo está em memória (List). Se quiser evoluir, adicione salvamento em arquivo `.csv` ou banco de dados.  
- **Match**: O enunciado fala sobre filtrar candidatos pelas competências requisitadas. Você pode criar um método que verifique o “match” das skills.  
- **Validação**: Talvez queira validar campos (por exemplo, CPF, CNPJ, campos vazios).  
- **Refatoração**: Com o tempo, extrair a lógica de menu para uma classe separada, isolar cadastro/listagem em “services” etc.

---


