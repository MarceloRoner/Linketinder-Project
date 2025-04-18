package context

import dao.*
import service.*
import utils.DatabaseUtils
import java.sql.Connection

class AppContext {

    private final Connection conn

    final ICandidatoDAO   candidatoDAO
    final IEmpresaDAO     empresaDAO
    final IVagaDAO        vagaDAO
    final ICompetenciaDAO competenciaDAO

    final CandidatoService  candidatoService
    final EmpresaService    empresaService
    final VagaService       vagaService
    final CompetenciaService competenciaService
    final CurtidaService    curtidaService

    AppContext() {
        conn = DatabaseUtils.getConnection()
        candidatoDAO   = new CandidatoDAO(conn)
        empresaDAO     = new EmpresaDAO(conn)
        vagaDAO        = new VagaDAO(conn)
        competenciaDAO = new CompetenciaDAO(conn)

        candidatoService   = new CandidatoService(candidatoDAO)
        empresaService     = new EmpresaService(empresaDAO)
        vagaService        = new VagaService(vagaDAO)
        competenciaService = new CompetenciaService(competenciaDAO)
        curtidaService     = new CurtidaService()
    }

    void closeConnection() {
        if (conn && !conn.isClosed()) conn.close()
    }
}
