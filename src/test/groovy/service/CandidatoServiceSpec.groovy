package service

import spock.lang.Specification
import domain.Candidato
import java.time.LocalDate

class CandidatoServiceSpec extends Specification {

    def service = new CandidatoService()

    def "deve cadastrar e buscar candidato por ID"() {
        given:
        def candidato = new Candidato("Ana", "Lima", LocalDate.of(1990, 5, 10), "ana_teste@mail.com", "123456789", 33, "Brasil", "SP", "00000-000", "Fullstack", ["Java", "Spring"], "senha123")
        service.cadastrar(candidato)
        def salvo = service.listarTodos().find { it.email == "ana_teste@mail.com" }

        when:
        def resultado = service.buscarPorId(salvo.id)

        then:
        resultado.nome == "Ana"
    }

}
