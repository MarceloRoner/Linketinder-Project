package integration

import spock.lang.Specification
import java.net.HttpURLConnection

class CandidatoGetEndpointSpec extends Specification {

    def "deve retornar lista de candidatos com sucesso"() {
        given:
        def url = new URL("http://localhost:8080/candidatos")
        def connection = (HttpURLConnection) url.openConnection()
        connection.setRequestMethod("GET")

        when:
        def responseCode = connection.responseCode
        def responseBody = connection.inputStream.text.trim()

        then:
        responseCode == 200
        responseBody.startsWith("[")
        responseBody.contains("marcelo@test.com")
    }
}

