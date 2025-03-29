import domain.Candidato
import domain.Empresa
import domain.Vaga
import spock.lang.Specification

    class CurtidaSpec extends Specification {
        def "deve retornar verdadeiro se candidato e empresa curtiram"() {
            given:
            def cand = new Candidato(
                    "João", "Nunes",
                    LocalDate.of(2004, 10, 7),
                    "joao@example.com",
                    "123.456.789-00",
                    30,
                    "Brasil", "SP", "01000-000",
                    "Dev",
                    ["Java"],
                    "senha123"
            )

            def emp = new Empresa(
                    "Arroz-Gostoso",
                    "rh@arrozgostoso.com",
                    "12.345.678/0001-00",
                    "Brasil", "SP", "01000-111",
                    "Ramo alimentício",
                    "senha123",
                    ["Java"]
            )

            def vaga = new Vaga(
                    1,
                    "Desenvolvedor Java",
                    emp,
                    ["Java"],
                    "São Paulo"
            )

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
            def cand = new Candidato(
                    "Maria", "Silva",
                    LocalDate.of(1998, 5, 20),
                    "maria@example.com",
                    "987.654.321-00",
                    25,
                    "Brasil", "RJ", "20000-000",
                    "QA",
                    ["Testes"],
                    "senha123"
            )

            def emp = new Empresa(
                    "Tech Solutions",
                    "careers@techsolutions.com",
                    "11.111.111/0001-11",
                    "Brasil", "MG", "30000-333",
                    "Consultoria em TI",
                    "senha123",
                    ["DevOps"]
            )

            def vaga = new Vaga(
                    2,
                    "QA Engineer",
                    emp,
                    ["Testes"],
                    "Belo Horizonte"
            )

            and:
            def curtida = new Curtida(cand, vaga)

            when:
            curtida.candidatoCurtiu = true
            curtida.empresaCurtiu = false

            then:
            curtida.isMatch() == false
        }


    }