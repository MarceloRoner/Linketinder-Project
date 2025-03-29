import domain.Candidato
import spock.lang.Specification

import java.time.LocalDate

class CandidatoSpec extends Specification {

    def "deve criar um candidato corretamente"() {
        given:
        def candidato = new Candidato(
                "João",
                "Silva",
                LocalDate.of(2004, 7, 12),
                "joao@example.com",
                "123.456.789-00",
                30,
                "Brasil",
                "SP",
                "01000-000",
                "Desenvolvedor FullStack",
                ["Java", "Groovy"],
                "senha123",

        )

        expect:
        candidato.nome == "João"
        candidato.email == "joao@example.com"
        candidato.dataNascimento == LocalDate.of(2004, 7, 12)
        candidato.pais == "Brasil"
        candidato.idade == 30
        candidato.estado == "SP"
        candidato.cep == "01000-000"
        candidato.descricao == "Desenvolvedor FullStack"
        candidato.competencias == ["Java", "Groovy"]
        candidato.senha == "senha123"
    }

    def "deve alterar nome e email através dos setters"() {
        given:
        def candidato = new Candidato(
                "Maria",
                "Roner",
                LocalDate.of(2003,06,02),
                "maria@example.com",
                "987.654.321-00",
                25,
                "Brazil",
                "RJ",
                "20000-000",
                "QA Engineer",
                ["Testes Automatizados"],
                "senha123"
        )

        when:
        candidato.setNome("Maria Souza")
        candidato.setEmail("souza.maria@example.com")

        then:
        candidato.getNome() == "Maria Souza"
        candidato.getEmail() == "souza.maria@example.com"
    }
}
