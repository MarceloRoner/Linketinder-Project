package service

import spock.lang.Specification
import domain.Empresa

class EmpresaServiceSpec extends Specification {

    def service = new EmpresaService()

    def "deve cadastrar e buscar empresa por ID"() {
        given:
        def empresa = new Empresa("DevHouse", "devhouse@mail.com", "11223344556677", "Brasil", "SP", "00000-000", "Desenvolvimento", "senha123", ["Kotlin"])
        service.cadastrar(empresa)
        def salva = service.listarTodas().find { it.cnpj == "11223344556677" }

        when:
        def resultado = service.buscarPorId(salva.id)

        then:
        resultado.nome == "DevHouse"
    }
}
