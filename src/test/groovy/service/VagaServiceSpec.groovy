package service

import spock.lang.Specification
import model.Empresa
import model.Vaga
import dao.IVagaDAO

class VagaServiceSpec extends Specification {

    def dao = Stub(IVagaDAO)
    def service = new VagaService(dao)

    def "deve buscar vaga por ID"() {
        given:
        def empresa = new Empresa(
                "AlfaTech", "alfa@tech.com", "99887766554433",
                "Brasil", "MG", "11111-111", "TI", "senha", ["Python"]
        )
        empresa.id = 1

        def vaga = new Vaga(77, "Estágio Dev", empresa, ["HTML", "CSS"], "Remoto")

        dao.listarVagas() >> [vaga]

        when:
        def resultado = service.buscarPorId(77)

        then:
        resultado != null
        resultado.titulo == "Estágio Dev"
        resultado.empresa.nome == "AlfaTech"
    }
}
