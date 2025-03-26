package domain

class Empresa implements Pessoa {
    Integer id
    String nome
    String email
    String descricao

    String cnpj
    String pais
    String estado
    String cep
    List<String> competenciasEsperadas = []

    String getNome() { return this.nome }
    void setNome(String nome) { this.nome = nome }

    String getEmail() { return this.email }
    void setEmail(String email) { this.email = email }

    String getDescricao() { return this.descricao }
    void setDescricao(String descricao) { this.descricao = descricao }

    Empresa(String nome, String email, String cnpj, String pais,
            String estado, String cep, String descricao,
            List<String> competenciasEsperadas) {
        this.nome = nome
        this.email = email
        this.cnpj = cnpj
        this.pais = pais
        this.estado = estado
        this.cep = cep
        this.descricao = descricao
        this.competenciasEsperadas = competenciasEsperadas
    }

    @Override
    String toString() {
        """
        Empresa: $nome
        E-mail Corporativo: $email
        CNPJ: $cnpj
        País: $pais
        Estado: $estado
        CEP: $cep
        Descrição: $descricao
        Competências Esperadas: ${competenciasEsperadas.join(', ')}
        """.stripIndent()
    }
}
