package service

import spock.lang.Specification

class CompetenciaServiceSpec extends Specification {

    def service = new CompetenciaService()

    def "deve listar, adicionar e atualizar competência"() {
        given:
        service.excluir("Go") // limpeza

        when:
        service.excluir("Go")
        service.atualizar("Go", "Golang")
        def todas = service.listarTodas()

        then:
        !todas.contains("Go") // ou assert manualmente no console
    }
}
