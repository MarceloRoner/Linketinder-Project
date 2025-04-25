package service

import dao.IEmpresaDAO
import model.Empresa

class EmpresaService {

    private final IEmpresaDAO dao

    EmpresaService(IEmpresaDAO dao) {
        this.dao = dao
    }

    List<Empresa> listarTodas() {
        return dao.listarEmpresas()
    }

    void cadastrar(Empresa empresa) {
        dao.inserirEmpresa(empresa)
    }

    void atualizar(Empresa empresa) {
        dao.atualizarEmpresa(empresa)
    }

    void excluir(int id) {
        dao.excluirEmpresa(id)
    }

    Empresa buscarPorId(int id) {
        return listarTodas().find { it.id == id }
    }
}
