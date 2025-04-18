package service

import dao.VagaDAO
import domain.Vaga

class VagaService {

    List<Vaga> listarTodas() {
        return VagaDAO.listarVagas()
    }

    void cadastrar(Vaga vaga) {
        VagaDAO.inserirVaga(vaga)
    }

    void atualizar(Vaga vaga) {
        VagaDAO.atualizarVaga(vaga)
    }

    void excluir(int id) {
        VagaDAO.excluirVaga(id)
    }

    Vaga buscarPorId(int id) {
        return listarTodas().find { it.id == id }
    }

    List<Vaga> listarPorEmpresa(int empresaId) {
        return listarTodas().findAll { it.empresa?.id == empresaId }
    }
}
