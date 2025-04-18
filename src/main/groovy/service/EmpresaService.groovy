package service

import dao.EmpresaDAO
import domain.Empresa

class EmpresaService {

    List<Empresa> listarTodas() {
        return EmpresaDAO.listarEmpresas()
    }

    void cadastrar(Empresa empresa) {
        EmpresaDAO.inserirEmpresa(empresa)
    }

    void atualizar(Empresa empresa) {
        EmpresaDAO.atualizarEmpresa(empresa)
    }

    void excluir(int id) {
        EmpresaDAO.excluirEmpresa(id)
    }

    Empresa buscarPorId(int id) {
        return listarTodas().find { it.id == id }
    }
}
