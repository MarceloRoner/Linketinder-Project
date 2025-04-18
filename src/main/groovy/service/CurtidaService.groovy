package service

import domain.Candidato
import domain.Vaga
import domain.Curtida

class CurtidaService {

    private final List<Curtida> curtidas = []

    void curtirPorCandidato(Candidato candidato, Vaga vaga) {
        Curtida curtida = buscarCurtida(candidato, vaga)
        if (curtida) {
            curtida.candidatoCurtiu = true
        } else {
            curtidas << new Curtida(candidato, vaga)
        }
        exibirStatusDeMatch(candidato, vaga)
    }

    void curtirPorEmpresa(Candidato candidato, Vaga vaga) {
        Curtida curtida = buscarCurtida(candidato, vaga)
        if (curtida) {
            curtida.empresaCurtiu = true
        } else {
            Curtida nova = new Curtida(candidato, vaga)
            nova.empresaCurtiu = true
            curtidas << nova
        }
        exibirStatusDeMatch(candidato, vaga)
    }

    List<Curtida> listarTodas() {
        return curtidas
    }

    Curtida buscarCurtida(Candidato candidato, Vaga vaga) {
        return curtidas.find { it.candidato == candidato && it.vaga == vaga }
    }

    private void exibirStatusDeMatch(Candidato candidato, Vaga vaga) {
        Curtida curtida = buscarCurtida(candidato, vaga)
        if (curtida?.isMatch()) {
            println ">>> MATCH entre ${candidato.nome} e a vaga '${vaga.titulo}'! <<<"
        }
    }
}
