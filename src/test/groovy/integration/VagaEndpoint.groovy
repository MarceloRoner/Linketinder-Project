package integration
import spock.lang.Specification
import java.net.HttpURLConnection

class VagaEndpointSpec extends Specification {

    def "não deve cadastrar vaga com empresa inexistente"() {
        given:
        def url = new URL("http://localhost:8080/vagas")
        def connection = (HttpURLConnection) url.openConnection()
        connection.setRequestMethod("POST")
        connection.setDoOutput(true)
        connection.setRequestProperty("Content-Type", "application/json")

        def json = '''
        {
          "titulo": "Dev Jr",
          "empresa": { "id": 9999 },
          "competencias": ["Groovy"],
          "modalidade": "Remoto"
        }
        '''

        connection.outputStream.withWriter("UTF-8") { it.write(json) }

        when:
        def responseCode = connection.responseCode
        def responseBody = connection.errorStream?.text?.trim()

        then:
        responseCode == 400
        responseBody.contains("empresa inexistente")
    }
}
