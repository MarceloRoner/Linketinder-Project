package controller.rest

import com.sun.net.httpserver.HttpExchange
import dao.CandidatoDAO
import service.CandidatoService
import model.Candidato
import utils.ConnectionFactory

import java.sql.Connection

class CandidateHandler extends JsonHandler {

    private static final Connection CONN =
            ConnectionFactory.instance.connection

    private final CandidatoService service =
            new CandidatoService(new CandidatoDAO(CONN))


    @Override
    void handle(HttpExchange ex) {
        try {
            switch (ex.requestMethod) {
                case 'POST': handlePost(ex); break
                case 'GET':  handleGetAll(ex); break
                default:     ex.sendResponseHeaders(405, -1)
            }
        } finally { ex.close() }
    }


    private void handlePost(HttpExchange ex) {
        try {
            def cand = gson.fromJson(body(ex), Candidato)

            service.cadastrar(cand)

            ex.responseHeaders.add('Location', "/candidatos/${cand.id}")
            ex.sendResponseHeaders(201, -1)
        } catch (Exception e) {
            send(ex, 400, [erro: e.message])
        }
    }

    private void handleGetAll(HttpExchange ex) {
        def todos = service.listarTodos()
        send(ex, 200, todos)
    }
}
