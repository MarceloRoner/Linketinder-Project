import spock.lang.Specification

class CandidatoSpec extends Specification {

    def "deve criar um candidato corretamente"() {
        given:
        def candidato = new Candidato(
                "João",
                "joao@example.com",
                "123.456.789-00",
                30,
                "SP",
                "01000-000",
                "Desenvolvedor FullStack",
                ["Java", "Groovy"]
        )

        expect:
        candidato.nome == "João"
        candidato.email == "joao@example.com"
        candidato.idade == 30
        candidato.estado == "SP"
        candidato.cep == "01000-000"
        candidato.descricao == "Desenvolvedor FullStack"
        candidato.competencias == ["Java", "Groovy"]
    }

    def "deve alterar nome e email através dos setters"() {
        given:
        def candidato = new Candidato(
                "Maria",
                "maria@example.com",
                "987.654.321-00",
                25,
                "RJ",
                "20000-000",
                "QA Engineer",
                ["Testes Automatizados"]
        )

        when:
        candidato.setNome("Maria Souza")
        candidato.setEmail("souza.maria@example.com")

        then:
        candidato.getNome() == "Maria Souza"
        candidato.getEmail() == "souza.maria@example.com"
    }
}
