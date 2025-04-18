package service

import dao.CompetenciaDAO
import utils.DatabaseUtils

import java.sql.Connection

class CompetenciaService {

    List<String> listarTodas() {
        return CompetenciaDAO.listarTodasCompetencias()
    }

    void excluir(String nome) {
        CompetenciaDAO.excluirCompetencia(nome)
    }

    void atualizar(String nomeAntigo, String nomeNovo) {
        CompetenciaDAO.atualizarCompetencia(nomeAntigo, nomeNovo)
    }

    int obterOuInserir(String nome, Connection conn) {
        return CompetenciaDAO.inserirOuBuscarCompetencia(conn, nome)
    }
}
