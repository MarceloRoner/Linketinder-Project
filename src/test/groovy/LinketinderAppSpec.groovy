import domain.Candidato
import domain.Empresa
import spock.lang.Specification

class LinketinderAppSpec extends Specification {

    def setup() {
        // Limpamos as listas estáticas antes de cada teste
        LinketinderApp.candidatos.clear()
        LinketinderApp.empresas.clear()
        LinketinderApp.vagas.clear()
        LinketinderApp.curtidas.clear()
    }

    def "deve adicionar um novo candidato na lista"() {
        given:
        def cand = new Candidato(
                "NovoCandidato",
                "novo@example.com",
                "111.222.333-44",
                20,
                "SP",
                "00000-000",
                "Estagiário",
                ["Groovy", "Java"]
        )

        when:
        LinketinderApp.adicionarCandidato(cand)

        then:
        LinketinderApp.candidatos.size() == 1
        LinketinderApp.candidatos[0].nome == "NovoCandidato"
    }

    def "deve adicionar uma nova empresa na lista"() {
        given:
        def emp = new Empresa(
                "NovaEmpresa",
                "contato@novaempresa.com",
                "99.999.999/0001-99",
                "Brasil",
                "RJ",
                "20000-000",
                "Ramo de testes",
                ["Marketing"]
        )

        when:
        LinketinderApp.adicionarEmpresa(emp)

        then:
        LinketinderApp.empresas.size() == 1
        LinketinderApp.empresas[0].nome == "NovaEmpresa"
    }
}
