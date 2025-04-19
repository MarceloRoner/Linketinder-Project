package service

import dao.ICandidatoDAO
import domain.Candidato

class CandidatoService {

    private final ICandidatoDAO dao

    CandidatoService(ICandidatoDAO dao) {
        this.dao = dao
    }

    List<Candidato> listarTodos() {
        return dao.listarCandidatos()
    }

    void cadastrar(Candidato candidato) {
        dao.inserirCandidato(candidato)
    }

    void atualizar(Candidato candidato) {
        dao.atualizarCandidato(candidato)
    }

    void excluir(int id) {
        dao.excluirCandidato(id)
    }

    Candidato buscarPorId(int id) {
        return listarTodos().find { it.id == id }
    }
}
