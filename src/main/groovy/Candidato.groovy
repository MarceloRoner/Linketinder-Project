class Candidato implements Pessoa {

    String nome
    String email
    String descricao

    String cpf
    Integer idade
    String estado
    String cep
    List<String> competencias = []

    String getNome() { return this.nome }
    void setNome(String nome) { this.nome = nome }

    String getEmail() { return this.email }
    void setEmail(String email) { this.email = email }

    String getDescricao() { return this.descricao }
    void setDescricao(String descricao) { this.descricao = descricao }

    Candidato(String nome, String email, String cpf, Integer idade, String estado, String cep, String descricao, List<String> competencias) {
        this.nome = nome
        this.email = email
        this.cpf = cpf
        this.idade = idade
        this.estado = estado
        this.cep = cep
        this.descricao = descricao
        this.competencias = competencias
    }

    @Override
    String toString() {
        """
        Candidato: $nome
        E-mail: $email
        CPF: $cpf
        Idade: $idade
        Estado: $estado
        CEP: $cep
        Descrição: $descricao
        Competências: ${competencias.join(', ')}
        """.stripIndent()
    }
}
