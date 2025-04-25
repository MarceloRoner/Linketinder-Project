package service

import model.Candidato
import model.Curtida
import model.Empresa
import model.Vaga

class LinketinderFacade {

    private final CandidatoService   candidatoSvc
    private final EmpresaService     empresaSvc
    private final VagaService        vagaSvc
    private final CompetenciaService competenciaSvc
    private final CurtidaService     curtidaSvc

    LinketinderFacade(CandidatoService candidatoSvc,
                      EmpresaService   empresaSvc,
                      VagaService      vagaSvc,
                      CompetenciaService competenciaSvc,
                      CurtidaService     curtidaSvc) {
        this.candidatoSvc   = candidatoSvc
        this.empresaSvc     = empresaSvc
        this.vagaSvc        = vagaSvc
        this.competenciaSvc = competenciaSvc
        this.curtidaSvc     = curtidaSvc
    }


    List<Candidato> listarCandidatos()         { candidatoSvc.listarTodos() }
    List<Empresa>   listarEmpresas()           { empresaSvc.listarTodas() }
    List<Vaga>      listarVagas()              { vagaSvc.listarTodas() }
    List<String>    listarCompetencias()       { competenciaSvc.listarTodas() }
    List<Curtida>   listarCurtidas()           { curtidaSvc.listarTodas() }


    void cadastrarCandidato(Candidato c)       { candidatoSvc.cadastrar(c) }
    void cadastrarEmpresa(Empresa e)           { empresaSvc.cadastrar(e) }
    void cadastrarVaga(Vaga v)                 { vagaSvc.cadastrar(v) }


    void atualizarCandidato(Candidato c)       { candidatoSvc.atualizar(c) }
    void atualizarEmpresa(Empresa e)           { empresaSvc.atualizar(e) }
    void atualizarVaga(Vaga v)                 { vagaSvc.atualizar(v)  }
    void atualizarCompetencia(String a,String n){ competenciaSvc.atualizar(a,n) }


    void excluirCandidato(int id)              { candidatoSvc.excluir(id) }
    void excluirEmpresa(int id)                { empresaSvc.excluir(id) }
    void excluirVaga(int id)                   { vagaSvc.excluir(id) }
    void excluirCompetencia(String nome)       { competenciaSvc.excluir(nome) }


    void curtirPorCandidato(Candidato c,Vaga v){ curtidaSvc.curtirPorCandidato(c,v) }
    void curtirPorEmpresa(Candidato c,Vaga v)  { curtidaSvc.curtirPorEmpresa(c,v) }
}
