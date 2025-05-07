package integration
import spock.lang.Specification

class PingEndpointSpec extends Specification {

    def "deve responder pong no endpoint /ping"() {
        given:
        def url = new URL("http://localhost:8080/ping")
        def connection = (HttpURLConnection) url.openConnection()
        connection.requestMethod = "GET"

        when:
        def responseCode = connection.responseCode
        def responseBody = connection.inputStream.text.trim()

        then:
        responseCode == 200
        responseBody == "pong"
    }
}
