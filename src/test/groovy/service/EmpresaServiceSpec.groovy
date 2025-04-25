package service

import spock.lang.Specification
import model.Empresa
import dao.IEmpresaDAO

class EmpresaServiceSpec extends Specification {

    def dao = Stub(IEmpresaDAO)
    def service = new EmpresaService(dao)

    def "deve buscar empresa por ID"() {
        given:
        def empresa = new Empresa(
                "DevHouse", "devhouse@mail.com", "11223344556677",
                "Brasil", "SP", "00000-000", "Desenvolvimento",
                "senha123", ["Kotlin"]
        )
        empresa.id = 99

        dao.listarEmpresas() >> [empresa]

        when:
        def resultado = service.buscarPorId(99)

        then:
        resultado != null
        resultado.nome == "DevHouse"
    }
}
