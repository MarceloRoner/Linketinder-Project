package service

import dao.ICompetenciaDAO

class CompetenciaService {

    private final ICompetenciaDAO dao

    CompetenciaService(ICompetenciaDAO dao) {
        this.dao = dao
    }

    List<String> listarTodas() {
        return dao.listarTodasCompetencias()
    }

    void excluir(String nome) {
        dao.excluirCompetencia(nome)
    }

    void atualizar(String nomeAntigo, String nomeNovo) {
        dao.atualizarCompetencia(nomeAntigo, nomeNovo)
    }

    int obterOuInserir(String nome) {
        return dao.inserirOuBuscarCompetencia(nome)
    }
}
