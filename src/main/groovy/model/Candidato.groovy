package model

import java.time.LocalDate

class Candidato implements Pessoa {
    Integer id
    String nome
    String sobrenome
    LocalDate dataNascimento
    String email
    String descricao
    String cpf
    Integer idade
    String pais
    String estado
    String cep
    String senha
    List<String> competencias = []

    Candidato(String nome,
              String sobrenome,
              LocalDate dataNascimento,
              String email,
              String cpf,
              Integer idade,
              String pais,
              String estado,
              String cep,
              String descricao,
              List<String> competencias,
              String senha) {
        this.nome = nome
        this.sobrenome = sobrenome
        this.dataNascimento = dataNascimento
        this.email = email
        this.cpf = cpf
        this.idade = idade
        this.pais = pais
        this.estado = estado
        this.cep = cep
        this.descricao = descricao
        this.competencias = competencias ?: []
        this.senha = senha
    }

    @Override
    String toString() {
        """
        Candidato: $nome $sobrenome
        Data de Nascimento: ${dataNascimento}
        E-mail: $email
        CPF: $cpf
        Idade: $idade
        País: $pais
        Estado: $estado
        CEP: $cep
        Descrição: $descricao
        Competências: ${competencias.join(', ')}
        """.stripIndent()
    }
}
