package controller.rest
import utils.ConnectionFactory
import dao.EmpresaDAO
import service.EmpresaService
import model.Empresa
import com.sun.net.httpserver.HttpExchange

import java.sql.Connection

class CompanyHandler extends JsonHandler {
    private static final Connection CONN =
            ConnectionFactory.instance.connection

    private final EmpresaService service =
            new EmpresaService(new EmpresaDAO(CONN))

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
            Empresa e = gson.fromJson(body(ex), Empresa)
            service.cadastrar(e)
            ex.responseHeaders.add('Location', "/empresas/${e.id}")
            ex.sendResponseHeaders(201,-1)
        }catch(Exception e){
            send(ex,400,[erro:e.message])
        }
    }
    private void getAll(HttpExchange ex){ send(ex,200,service.listarTodos()) }
}
