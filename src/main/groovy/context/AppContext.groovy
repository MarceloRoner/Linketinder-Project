package context

import dao.*
import service.*
import controller.*
import utils.ConnectionFactory

import java.sql.Connection

class AppContext {

    private final Connection conn

    final ICandidatoDAO   candidatoDAO
    final IEmpresaDAO     empresaDAO
    final IVagaDAO        vagaDAO
    final ICompetenciaDAO competenciaDAO

    final CandidatoService   candidatoService
    final EmpresaService     empresaService
    final VagaService        vagaService
    final CompetenciaService competenciaService
    final CurtidaService     curtidaService

    final LinketinderFacade  facade

    final CandidatoController candidatoCtl
    final EmpresaController   empresaCtl
    final VagaController      vagaCtl

    AppContext() {
        conn = ConnectionFactory.getInstance().getConnection()

        candidatoDAO   = new CandidatoDAO(conn)
        empresaDAO     = new EmpresaDAO(conn)
        vagaDAO        = new VagaDAO(conn)
        competenciaDAO = new CompetenciaDAO(conn)

        candidatoService   = new CandidatoService(candidatoDAO)
        empresaService     = new EmpresaService(empresaDAO)
        vagaService        = new VagaService(vagaDAO)
        competenciaService = new CompetenciaService(competenciaDAO)
        curtidaService     = new CurtidaService()

        facade = new LinketinderFacade(
                candidatoService, empresaService,
                vagaService,      competenciaService,
                curtidaService)

        candidatoCtl = new CandidatoController(facade)
        empresaCtl   = new EmpresaController(facade)
        vagaCtl      = new VagaController(facade)
    }

    void closeConnection() {
        if (conn && !conn.isClosed()) conn.close()
    }
}
