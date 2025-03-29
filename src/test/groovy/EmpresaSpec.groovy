import domain.Empresa
import spock.lang.Specification

class EmpresaSpec extends Specification {

    def "deve criar uma empresa corretamente"() {
        given:
        def empresa = new Empresa(
                "Arroz-Gostoso",                            // nome
                "rh@arrozgostoso.com",                      // email
                "12.345.678/0001-00",                       // cnpj
                "Brasil",                                   // pais
                "SP",                                       // estado
                "01000-111",                                // cep
                "Empresa do ramo alimentício",             // descricao
                "senha123",                                 // senha
                ["Java", "Groovy", "Banco de Dados"]       // competenciasEsperadas
        )
        expect:
        empresa.nome == "Arroz-Gostoso"
        empresa.email == "rh@arrozgostoso.com"
        empresa.cnpj == "12.345.678/0001-00"
        empresa.estado == "SP"
        empresa.cep == "01000-111"
        empresa.descricao == "Empresa do ramo alimentício"
        empresa.competenciasEsperadas == ["Java", "Groovy", "Banco de Dados"]
        empresa.senha == "senha123"
    }
}
