package controller

import service.LinketinderFacade
import model.Empresa

class EmpresaController {
    private final LinketinderFacade facade
    EmpresaController(LinketinderFacade f){ this.facade = f }

    List<Empresa> listar()             { facade.listarEmpresas() }
    void criar(Empresa e)              { facade.cadastrarEmpresa(e) }
    void atualizar(Empresa e)          { facade.atualizarEmpresa(e) }
    void excluir(Integer id)           { facade.excluirEmpresa(id) }
}
