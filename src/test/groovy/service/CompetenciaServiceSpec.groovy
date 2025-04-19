package service

import spock.lang.Specification
import dao.ICompetenciaDAO

class CompetenciaServiceSpec extends Specification {

    def dao = Stub(ICompetenciaDAO)
    def service = new CompetenciaService(dao)

    def "deve excluir e atualizar competência"() {
        given:
        def competencias = ["Go", "Java", "Python"]
        dao.listarTodasCompetencias() >> competencias
        dao.atualizarCompetencia(_, _) >> { oldName, newName ->
        }
        dao.excluirCompetencia(_) >> { nome -> }

        when:
        service.excluir("Go")
        service.atualizar("Go", "Golang")
        def todas = service.listarTodas()

        then:
        todas.contains("Go") 
    }
}
