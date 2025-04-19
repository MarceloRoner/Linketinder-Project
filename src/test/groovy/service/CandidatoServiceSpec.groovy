package service

import spock.lang.Specification
import domain.Candidato
import dao.ICandidatoDAO
import java.time.LocalDate

class CandidatoServiceSpec extends Specification {

    def dao = Stub(ICandidatoDAO)
    def service = new CandidatoService(dao)

    def "deve buscar candidato por ID"() {
        given:
        def candidato = new Candidato(
                "Ana", "Lima",
                LocalDate.of(1990, 5, 10),
                "ana_teste@mail.com", "123456789", 33,
                "Brasil", "SP", "00000-000",
                "Fullstack", ["Java", "Spring"], "senha123"
        )
        candidato.id = 42

        dao.listarCandidatos() >> [candidato]

        when:
        def resultado = service.buscarPorId(42)

        then:
        resultado != null
        resultado.nome == "Ana"
    }
}
