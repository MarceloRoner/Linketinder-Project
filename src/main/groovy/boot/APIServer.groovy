package boot

import com.sun.net.httpserver.*
import controller.rest.CandidateHandler
import controller.rest.CompanyHandler
import controller.rest.JobHandler
import java.nio.charset.StandardCharsets

class ApiServer {
    static void main(String[] args) {
        def port   = 8080
        def server = HttpServer.create(new InetSocketAddress(port), 0)

        server.createContext('/ping', new HttpHandler() {
            @Override
            void handle(HttpExchange ex) {
                byte[] out = 'pong'.bytes
                ex.responseHeaders.add('Content-Type', 'text/plain')
                ex.sendResponseHeaders(200, out.length)
                ex.responseBody.write(out)
                ex.close()
            }
        })

        server.createContext('/candidatos', new CandidateHandler())
        println '>> /candidatos pronto'
        server.createContext('/empresas', new CompanyHandler())
        println '>> /empresas pronto'
        server.createContext('/vagas', new JobHandler())
        println '>> /vagas pronto'

        server.executor = null
        server.start()
        println "🚀  API em http://localhost:$port (Ctrl+C pra parar)"
    }
}
