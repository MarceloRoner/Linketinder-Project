package controller

import service.LinketinderFacade
import model.Vaga

class VagaController {
    private final LinketinderFacade facade
    VagaController(LinketinderFacade f){ this.facade = f }

    List<Vaga> listar()                { facade.listarVagas() }
    void criar(Vaga v)                 { facade.cadastrarVaga(v) }
    void atualizar(Vaga v)             { facade.atualizarVaga(v) }
    void excluir(Integer id)           { facade.excluirVaga(id) }
}
