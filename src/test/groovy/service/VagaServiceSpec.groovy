package service

import spock.lang.Specification
import domain.Empresa
import domain.Vaga

class VagaServiceSpec extends Specification {

    def empresaService = new EmpresaService()
    def vagaService = new VagaService()

    def "deve cadastrar e buscar vaga por ID"() {
        given:
        def empresa = new Empresa("AlfaTech", "alfa@tech.com", "99887766554433", "Brasil", "MG", "11111-111", "TI", "senha", ["Python"])
        empresaService.cadastrar(empresa)

        def empresaSalva = empresaService.listarTodas().find { it.cnpj == "99887766554433" }
        assert empresaSalva != null

        def vaga = new Vaga(null, "Estágio Dev", empresaSalva, ["HTML", "CSS"], "Remoto")
        vagaService.cadastrar(vaga)

        def vagaSalva = vagaService.listarTodas().find { it.titulo == "Estágio Dev" }

        when:
        def resultado = vagaService.buscarPorId(vagaSalva.id)

        then:
        resultado.titulo == "Estágio Dev"
        resultado.empresa.nome == "AlfaTech"
    }
}
