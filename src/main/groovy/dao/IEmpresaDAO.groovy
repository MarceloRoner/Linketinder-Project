package dao

import domain.Empresa

interface IEmpresaDAO {
    List<Empresa> listarEmpresas()
    void inserirEmpresa(Empresa e)
    void atualizarEmpresa(Empresa e)
    void excluirEmpresa(int id)
}
