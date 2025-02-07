import spock.lang.Specification

class EmpresaSpec extends Specification {

    def "deve criar uma empresa corretamente"() {
        given:
        def empresa = new Empresa(
                "Arroz-Gostoso",
                "rh@arrozgostoso.com",
                "12.345.678/0001-00",
                "Brasil",
                "SP",
                "01000-111",
                "Empresa do ramo alimentício",
                ["Java", "Groovy", "Banco de Dados"]
        )

        expect:
        empresa.nome == "Arroz-Gostoso"
        empresa.email == "rh@arrozgostoso.com"
        empresa.cnpj == "12.345.678/0001-00"
        empresa.estado == "SP"
        empresa.cep == "01000-111"
        empresa.descricao == "Empresa do ramo alimentício"
        empresa.competenciasEsperadas == ["Java", "Groovy", "Banco de Dados"]
    }
}
