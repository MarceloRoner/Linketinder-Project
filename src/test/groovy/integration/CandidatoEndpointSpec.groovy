package integration

import spock.lang.Specification
import java.net.HttpURLConnection

class CandidatoEndpointSpec extends Specification {

    def "deve cadastrar um novo candidato com sucesso"() {
        given:
        def url = new URL("http://localhost:8080/candidatos")
        def connection = (HttpURLConnection) url.openConnection()
        connection.setRequestMethod("POST")
        connection.setDoOutput(true)
        connection.setRequestProperty("Content-Type", "application/json")

        def json = '''
        {
          "nome": "Marcelo",
          "sobrenome": "Roner",
          "dataNascimento": "2000-01-01",
          "email": "marcelo@test.com",
          "cpf": "12345678900",
          "idade": 24,
          "pais": "Brasil",
          "estado": "GO",
          "cep": "74000-000",
          "descricao": "Backend Developer",
          "competencias": ["Groovy", "REST", "PostgreSQL"],
          "senha": "supersecreta"
        }
        '''

        connection.outputStream.withWriter("UTF-8") { it.write(json) }

        when:
        def responseCode = connection.responseCode
        def locationHeader = connection.getHeaderField("Location")

        then:
        responseCode == 201
        locationHeader != null
        locationHeader.startsWith("/candidatos/")
    }
}

