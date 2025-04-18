package service

import dao.CandidatoDAO
import domain.Candidato

class CandidatoService {

    List<Candidato> listarTodos() {
        return CandidatoDAO.listarCandidatos()
    }

    void cadastrar(Candidato candidato) {
        CandidatoDAO.inserirCandidato(candidato)
    }

    void atualizar(Candidato candidato) {
        CandidatoDAO.atualizarCandidato(candidato)
    }

    void excluir(int id) {
        CandidatoDAO.excluirCandidato(id)
    }

    Candidato buscarPorId(int id) {
        return listarTodos().find { it.id == id }
    }
}
