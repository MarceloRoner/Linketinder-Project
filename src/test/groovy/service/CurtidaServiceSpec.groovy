package service

import spock.lang.Specification
import domain.Candidato
import domain.Empresa
import domain.Vaga
import java.time.LocalDate

class CurtidaServiceSpec extends Specification {

    def service = new CurtidaService()

    def "deve registrar curtida do candidato e verificar que não houve match"() {
        given:
        def candidato = new Candidato("João", "Silva", LocalDate.of(1995, 1, 1), "joao@mail.com", "123", 28, "Brasil", "GO", "74000-000", "Dev", ["Java"], "senha")
        def empresa = new Empresa("CodeCorp", "code@corp.com", "12345678000199", "Brasil", "GO", "74000-000", "TI", "senha", [])
        def vaga = new Vaga(1, "Dev Jr", empresa, ["Java"], "Remoto")

        when:
        service.curtirPorCandidato(candidato, vaga)

        then:
        def curtida = service.buscarCurtida(candidato, vaga)
        curtida != null
        curtida.candidatoCurtiu
        !curtida.empresaCurtiu
        !curtida.isMatch()
    }

    def "deve registrar match quando empresa também curte"() {
        given:
        def candidato = new Candidato("Maria", "Santos", LocalDate.of(1998, 2, 2), "maria@mail.com", "456", 25, "Brasil", "SP", "01000-000", "QA", ["Testes"], "senha")
        def empresa = new Empresa("TestCorp", "test@corp.com", "99887766000188", "Brasil", "SP", "01000-000", "Qualidade", "senha", [])
        def vaga = new Vaga(2, "Analista QA", empresa, ["Testes"], "Híbrido")

        when:
        service.curtirPorCandidato(candidato, vaga)
        service.curtirPorEmpresa(candidato, vaga)

        then:
        def curtida = service.buscarCurtida(candidato, vaga)
        curtida.isMatch()
    }
}
