package service

import dao.IVagaDAO
import model.Vaga

class VagaService {

    private final IVagaDAO dao

    VagaService(IVagaDAO dao) {
        this.dao = dao
    }

    List<Vaga> listarTodas() {
        return dao.listarVagas()
    }

    void cadastrar(Vaga vaga) {
        dao.inserirVaga(vaga)
    }

    void atualizar(Vaga vaga) {
        dao.atualizarVaga(vaga)
    }

    void excluir(int id) {
        dao.excluirVaga(id)
    }

    Vaga buscarPorId(int id) {
        return listarTodas().find { it.id == id }
    }

    List<Vaga> listarPorEmpresa(int empresaId) {
        return listarTodas().findAll { it.empresa?.id == empresaId }
    }
}
