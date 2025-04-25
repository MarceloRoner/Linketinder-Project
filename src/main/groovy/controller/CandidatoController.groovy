package controller

import service.LinketinderFacade
import model.Candidato

class CandidatoController {
    private final LinketinderFacade facade
    CandidatoController(LinketinderFacade f) { this.facade = f }

    List<Candidato> listar()           { facade.listarCandidatos() }
    void criar(Candidato c)            { facade.cadastrarCandidato(c) }
    void atualizar(Candidato c)        { facade.atualizarCandidato(c) }
    void excluir(Integer id)           { facade.excluirCandidato(id) }
}
