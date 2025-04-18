package domain

import spock.lang.Specification
import java.time.LocalDate

class CandidatoSpec extends Specification {

    def "deve criar candidato corretamente e retornar toString com nome completo"() {
        given:
        def candidato = new Candidato(
                "Lucas", "Nunes", LocalDate.of(1999, 3, 10),
                "lucas@mail.com", "789", 24,
                "Brasil", "DF", "70000-000",
                "Desenvolvedor Java", ["Java", "Spring Boot"], "senha123"
        )

        expect:
        candidato.nome == "Lucas"
        candidato.sobrenome == "Nunes"
        candidato.competencias.contains("Java")
        candidato.toString().contains("Lucas Nunes")
        candidato.toString().contains("Desenvolvedor Java")
    }
}
