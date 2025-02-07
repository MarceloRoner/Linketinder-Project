import spock.lang.Specification

class CurtidaSpec extends Specification {

    def "deve retornar verdadeiro se candidato e empresa curtiram"() {
        given:
        def cand = new Candidato("João", "joao@example.com", "123.456.789-00", 30, "SP", "01000-000", "Dev", ["Java"])
        def emp = new Empresa("Arroz-Gostoso", "rh@arrozgostoso.com", "12.345.678/0001-00", "Brasil", "SP", "01000-111", "Ramo alimentício", ["Java"])
        def vaga = new Vaga(1, "Desenvolvedor Java", emp, ["Java"])

        and:
        def curtida = new Curtida(cand, vaga)

        when:
        curtida.candidatoCurtiu = true
        curtida.empresaCurtiu = true

        then:
        curtida.isMatch() == true
    }

    def "deve retornar falso se apenas candidato curtiu"() {
        given:
        def cand = new Candidato("Maria", "maria@example.com", "987.654.321-00", 25, "RJ", "20000-000", "QA", ["Testes"])
        def emp = new Empresa("Tech Solutions", "careers@techsolutions.com", "11.111.111/0001-11", "Brasil", "MG", "30000-333", "Consultoria em TI", ["DevOps"])
        def vaga = new Vaga(2, "QA Engineer", emp, ["Testes"])

        and:
        def curtida = new Curtida(cand, vaga)

        when:
        curtida.candidatoCurtiu = true
        curtida.empresaCurtiu = false

        then:
        curtida.isMatch() == false
    }
}
