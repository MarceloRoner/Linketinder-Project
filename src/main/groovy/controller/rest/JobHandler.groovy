package controller.rest

import service.EmpresaService
import dao.VagaDAO
import dao.EmpresaDAO
import service.VagaService
import service.EmpresaService
import model.Vaga
import com.sun.net.httpserver.HttpExchange
import utils.ConnectionFactory

import java.sql.Connection

class JobHandler extends JsonHandler {
    private static final Connection CONN =
            ConnectionFactory.instance.connection
    private final VagaService vagaSvc   = new VagaService(new VagaDAO(CONN))
    private final EmpresaService empSvc = new EmpresaService(new EmpresaDAO(CONN))

    @Override
    void handle(HttpExchange ex){
        try{
            switch(ex.requestMethod){
                case 'POST': post(ex); break
                case 'GET' : getAll(ex); break
                default    : ex.sendResponseHeaders(405,-1)
            }
        }finally{ ex.close() }
    }

    private void post(HttpExchange ex){
        try{
            Vaga v = gson.fromJson(body(ex), Vaga)

            def emp = empSvc.listarTodas().find{ it.id == v.empresa.id }
            if(!emp){ send(ex,400,[erro:'empresa inexistente']); return }
            v.empresa = emp

            vagaSvc.cadastrar(v)
            ex.responseHeaders.add('Location', "/vagas/${v.id}")
            ex.sendResponseHeaders(201,-1)
        }catch(Exception e){
            send(ex,400,[erro:e.message])
        }
    }
    private void getAll(HttpExchange ex){ send(ex,200,vagaSvc.listarTodos()) }
}
