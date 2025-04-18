package domain

class Curtida {
    Candidato candidato
    Vaga vaga
    boolean candidatoCurtiu = true
    boolean empresaCurtiu = false

    Curtida(Candidato candidato, Vaga vaga) {
        this.candidato = candidato
        this.vaga = vaga
    }

    boolean isMatch() {
        return candidatoCurtiu && empresaCurtiu
    }

    String toString() {
        return "domain.Curtida(candidato=${candidato?.nome}, vaga=${vaga?.titulo}, match=${isMatch()})"
    }
}
