class Curtida {
    Candidato candidato
    Vaga vaga
    Empresa empresa

    boolean candidatoCurtiu = false
    boolean empresaCurtiu = false

    Curtida(Candidato candidato, Vaga vaga) {
        this.candidato = candidato
        this.vaga = vaga
    }

    void curtirEmpresa(Empresa empresa) {
        this.empresa = empresa
        this.empresaCurtiu = true
    }

    boolean isMatch() {
        return candidatoCurtiu && empresaCurtiu
    }

    @Override
    String toString() {
        """
        Curtida:
          Candidato: ${candidato.nome}
          Vaga: ${vaga.titulo}
          Empresa Dono da Vaga: ${vaga.empresa.nome}
          Empresa que curtiu Candidato: ${empresaCurtiu ? empresa?.nome : "Nenhuma"}
          Match? ${isMatch() ? "SIM" : "NÃO"}
        """.stripIndent()
    }
}