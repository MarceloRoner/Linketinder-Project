package controller.rest

import com.google.gson.*
import com.sun.net.httpserver.*
import java.nio.charset.StandardCharsets
import java.time.LocalDate

abstract class JsonHandler implements HttpHandler {

    protected final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate, new LocalDateAdapter())
            .create()

    protected String body(HttpExchange ex) {
        return new String(ex.requestBody.readAllBytes(),
                StandardCharsets.UTF_8)
    }

    protected void send(HttpExchange ex, int status, Object payload = null) {
        if (payload == null) {
            ex.sendResponseHeaders(status, -1)
            return
        }
        byte[] out = gson.toJson(payload).getBytes(StandardCharsets.UTF_8)
        ex.responseHeaders.add('Content-Type', 'application/json')
        ex.sendResponseHeaders(status, out.length)
        ex.responseBody.write(out)
    }
}
